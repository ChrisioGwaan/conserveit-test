package com.conserveit.controlbg.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.conserveit.controlbg.dto.NewBuildingDTO;
import com.conserveit.controlbg.entity.*;
import com.conserveit.controlbg.entity.interfaces.TemperatureControl;
import com.conserveit.controlbg.enums.CommonRoomEnum;
import com.conserveit.controlbg.mapper.*;
import com.conserveit.controlbg.service.BuildingService;
import com.conserveit.controlbg.utils.CommonConstants;
import com.conserveit.controlbg.utils.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building>  implements BuildingService {

    private final ApartmentMapper       apartmentMapper;
    private final ApartmentOwnerMapper  apartmentOwnerMapper;
    private final RoomMapper            roomMapper;
    private final CommonRoomMapper      commonRoomMapper;

    private final BigDecimal DEFAULT_TEMPERATURE    = new BigDecimal("20.0");
    private final BigDecimal DEFAULT_ROOM_TEMP_MIN  = new BigDecimal("10.0");
    private final BigDecimal DEFAULT_ROOM_TEMP_MAX  = new BigDecimal("40.0");
    private final BigDecimal DEFAULT_TOLERANCE      = new BigDecimal("0.5");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createBuilding(NewBuildingDTO newBuildingDTO) {

        if (newBuildingDTO.getApartments().size() != newBuildingDTO.getNumberOfApartment()
                || newBuildingDTO.getCommonRooms().size() != newBuildingDTO.getNumberOfCommonRoom()) {
            return R.failed("The number of apartments or common rooms is not equal to the number of apartments or common rooms");
        }

        // Set default temperature if temperature is empty
        if (ObjectUtils.isEmpty(newBuildingDTO.getTargetTemperature())) {
            newBuildingDTO.setTargetTemperature(DEFAULT_TEMPERATURE);
        }

        Building building = new Building();
        BeanUtils.copyProperties(newBuildingDTO, building);

        building.setCreateTime(LocalDateTime.now());
        this.save(building);

        // Save apartments, apartment owners and rooms
        if (!newBuildingDTO.getApartments().isEmpty()) {
            newBuildingDTO.getApartments().forEach(apartmentDTO -> {
                Apartment apartment = new Apartment();
                BeanUtils.copyProperties(apartmentDTO, apartment);
                apartment.setBuildingId(building.getId());
                apartment.setCreateTime(LocalDateTime.now());

                // Set a random temperature between 10.0 and 40.0 if temperature is empty
                if (ObjectUtils.isEmpty(apartment.getTargetTemperature())) {
                    apartment.setTargetTemperature(BigDecimal.valueOf(
                            new Random().nextDouble()
                                    * (DEFAULT_ROOM_TEMP_MAX.doubleValue() - DEFAULT_ROOM_TEMP_MIN.doubleValue())
                                    + DEFAULT_ROOM_TEMP_MIN.doubleValue()));
                }

                setTemperatureAndControl(apartment, building.getTargetTemperature());

                if (ObjectUtils.isEmpty(apartment.getNumberOfRoom())) {
                    apartment.setNumberOfRoom(apartmentDTO.getRooms().size());
                }

                apartmentMapper.insert(apartment);

                if (!apartmentDTO.getApartmentOwners().isEmpty()) {
                    apartmentDTO.getApartmentOwners().forEach(apartmentOwnerDTO -> {
                        ApartmentOwner apartmentOwner = new ApartmentOwner();
                        BeanUtils.copyProperties(apartmentOwnerDTO, apartmentOwner);
                        apartmentOwner.setApartmentId(apartment.getId());
                        apartmentOwner.setFirstName(apartmentOwnerDTO.getFirstName());
                        apartmentOwner.setLastName(apartmentOwnerDTO.getLastName());
                        apartmentOwner.setCreateTime(LocalDateTime.now());
                        apartmentOwnerMapper.insert(apartmentOwner);
                    });
                }

                if (!apartmentDTO.getRooms().isEmpty()) {
                    apartmentDTO.getRooms().forEach(roomDTO -> {
                        Room room = new Room();
                        BeanUtils.copyProperties(roomDTO, room);
                        room.setApartmentId(apartment.getId());
                        room.setCreateTime(LocalDateTime.now());

                        // Set a random temperature between 10.0 and 40.0 if temperature is empty
                        if (ObjectUtils.isEmpty(room.getTargetTemperature())) {
                            room.setTargetTemperature(BigDecimal.valueOf(
                                    new Random().nextDouble()
                                            * (DEFAULT_ROOM_TEMP_MAX.doubleValue() - DEFAULT_ROOM_TEMP_MIN.doubleValue())
                                            + DEFAULT_ROOM_TEMP_MIN.doubleValue()));
                        }

                        setTemperatureAndControl(room, building.getTargetTemperature());
                        roomMapper.insert(room);
                    });
                }
            });
        }

        if (!newBuildingDTO.getCommonRooms().isEmpty()) {
            newBuildingDTO.getCommonRooms().forEach(commonRoomDTO -> {
                CommonRoom commonRoom = new CommonRoom();
                BeanUtils.copyProperties(commonRoomDTO, commonRoom);
                commonRoom.setBuildingId(building.getId());
                commonRoom.setCreateTime(LocalDateTime.now());

                if (ObjectUtils.isEmpty(commonRoom.getType())) {
                    commonRoom.setType(CommonRoomEnum.UNKNOWN.getType());
                }

                // Set a random temperature between 10.0 and 40.0 if temperature is empty
                if (ObjectUtils.isEmpty(commonRoom.getTargetTemperature())) {
                    commonRoom.setTargetTemperature(BigDecimal.valueOf(
                            new Random().nextDouble()
                                    * (DEFAULT_ROOM_TEMP_MAX.doubleValue() - DEFAULT_ROOM_TEMP_MIN.doubleValue())
                                    + DEFAULT_ROOM_TEMP_MIN.doubleValue()));
                }

                setTemperatureAndControl(commonRoom, building.getTargetTemperature());
                commonRoomMapper.insert(commonRoom);
            });
        }

        return R.ok("Building created successfully");
    }

    // Temperature control logic
    <T extends TemperatureControl> void setTemperatureAndControl(T entity, BigDecimal buildingTemperature) {
        BigDecimal lowerBound = buildingTemperature.subtract(DEFAULT_TOLERANCE);
        BigDecimal upperBound = buildingTemperature.add(DEFAULT_TOLERANCE);

        if (ObjectUtils.isEmpty(entity.getCurrentTemperature())) {
            entity.setIsCooling(CommonConstants.OFF);
            entity.setIsHeating(CommonConstants.OFF);
            log.warn("Current temperature is empty for  with id: {}", entity.getId());
            return;
        }

        if (entity.getCurrentTemperature().compareTo(lowerBound) >= 0 && entity.getCurrentTemperature().compareTo(upperBound) <= 0) {
            entity.setIsCooling(CommonConstants.OFF);
            entity.setIsHeating(CommonConstants.OFF);
        } else if (entity.getCurrentTemperature().compareTo(buildingTemperature) > 0) {
            entity.setIsHeating(CommonConstants.OFF);
            entity.setIsCooling(CommonConstants.ON);
        } else if (entity.getCurrentTemperature().compareTo(buildingTemperature) < 0) {
            entity.setIsCooling(CommonConstants.OFF);
            entity.setIsHeating(CommonConstants.ON);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateBuilding() {
        return null;
    }

    @Override
    public R searchAllBuilding() {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R deleteBuilding() {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R temperatureControl(String buildingId, String newTempStr) {
        BigDecimal newTemp = new BigDecimal(newTempStr);
        Building building = this.getById(buildingId);

        if (ObjectUtils.isEmpty(building)) {
            return R.failed("Building not found in database");
        }

        building.setModifiedTime(LocalDateTime.now());
        building.setTargetTemperature(newTemp);
        this.updateById(building);

        List<CommonRoom> commonRoomList = commonRoomMapper.selectAllCommonRoomByBuildingId(buildingId);

        commonRoomList.forEach(commonRoom -> {
            if (ObjectUtils.isNotEmpty(commonRoom)) {
                commonRoom.setTargetTemperature(newTemp);
                commonRoom.setModifiedTime(LocalDateTime.now());
                setTemperatureAndControl(commonRoom, commonRoom.getTargetTemperature());
                commonRoomMapper.updateById(commonRoom);
            }
        });

        return R.ok("Temperature control successful");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateBuildingCurrentTemp(String buildingId, String newTempStr) {
        // Assume that the current temperature as manually input
        // Real implementation should be done by reading the temperature from the sensor

        Building building = this.getById(buildingId);

        if (ObjectUtils.isEmpty(building)) {
            return R.failed("Building not found");
        }

        building.setCurrentTemperature(new BigDecimal(newTempStr));
        building.setModifiedTime(LocalDateTime.now());
        this.updateById(building);

        return R.ok("Update building temperature successfully");
    }

}
