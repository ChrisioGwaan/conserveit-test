package com.conserveit.controlbg.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.conserveit.controlbg.dto.NewBuildingDTO;
import com.conserveit.controlbg.entity.*;
import com.conserveit.controlbg.entity.interfaces.TemperatureControl;
import com.conserveit.controlbg.mapper.*;
import com.conserveit.controlbg.service.BuildingService;
import com.conserveit.controlbg.utils.CommonConstants;
import com.conserveit.controlbg.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building>  implements BuildingService {

    private final ApartmentMapper       apartmentMapper;
    private final ApartmentOwnerMapper  apartmentOwnerMapper;
    private final RoomMapper            roomMapper;
    private final CommonRoomMapper      commonRoomMapper;

    private final BigDecimal DEFAULT_TEMPERATURE    = new BigDecimal("20.0");
    private final BigDecimal DEFAULT_ROOM_TEMP_MIN  = new BigDecimal("10.0");
    private final BigDecimal DEFAULT_ROOM_TEMP_MAX  = new BigDecimal("40.0");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createBuilding(NewBuildingDTO newBuildingDTO) {

        if (newBuildingDTO.getApartments().size() != newBuildingDTO.getNumberOfApartment()
                || newBuildingDTO.getCommonRooms().size() != newBuildingDTO.getNumberOfCommonRoom()) {
            return R.failed("The number of apartments or common rooms is not equal to the number of apartments or common rooms");
        }

        // Set default temperature if temperature is empty
        if (ObjectUtils.isEmpty(newBuildingDTO.getTemperature())) {
            newBuildingDTO.setTemperature(DEFAULT_TEMPERATURE);
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
                if (ObjectUtils.isEmpty(apartment.getTemperature())) {
                    apartment.setTemperature(BigDecimal.valueOf(
                            new Random().nextDouble()
                                    * (DEFAULT_ROOM_TEMP_MAX.doubleValue() - DEFAULT_ROOM_TEMP_MIN.doubleValue())
                                    + DEFAULT_ROOM_TEMP_MIN.doubleValue()));
                }

                setTemperatureAndControl(apartment, building.getTemperature());

                if (apartmentDTO.getRoomNumber() != apartmentDTO.getRooms().size()) {
                    apartment.setRoomNumber(apartmentDTO.getRooms().size());
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
                        if (ObjectUtils.isEmpty(room.getTemperature())) {
                            room.setTemperature(BigDecimal.valueOf(
                                    new Random().nextDouble()
                                            * (DEFAULT_ROOM_TEMP_MAX.doubleValue() - DEFAULT_ROOM_TEMP_MIN.doubleValue())
                                            + DEFAULT_ROOM_TEMP_MIN.doubleValue()));
                        }

                        setTemperatureAndControl(room, building.getTemperature());
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

                // Set a random temperature between 10.0 and 40.0 if temperature is empty
                if (ObjectUtils.isEmpty(commonRoom.getTemperature())) {
                    commonRoom.setTemperature(BigDecimal.valueOf(
                            new Random().nextDouble()
                                    * (DEFAULT_ROOM_TEMP_MAX.doubleValue() - DEFAULT_ROOM_TEMP_MIN.doubleValue())
                                    + DEFAULT_ROOM_TEMP_MIN.doubleValue()));
                }

                setTemperatureAndControl(commonRoom, building.getTemperature());
                commonRoomMapper.insert(commonRoom);
            });
        }

        return R.ok("Building created successfully");
    }

    // Temperature control logic
    <T extends TemperatureControl> void setTemperatureAndControl(T entity, BigDecimal buildingTemperature) {
        if (ObjectUtils.isEmpty(entity.getTemperature())) {
            entity.setTemperature(buildingTemperature);
            entity.setIsCooling(CommonConstants.OFF);
            entity.setIsHeating(CommonConstants.OFF);
        } else if (entity.getTemperature().compareTo(buildingTemperature) > 0) {
            entity.setIsHeating(CommonConstants.OFF);
            entity.setIsCooling(CommonConstants.ON);
        } else if (entity.getTemperature().compareTo(buildingTemperature) < 0) {
            entity.setIsCooling(CommonConstants.OFF);
            entity.setIsHeating(CommonConstants.ON);
        } else {
            entity.setIsCooling(CommonConstants.OFF);
            entity.setIsHeating(CommonConstants.OFF);
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

}
