package com.conserveit.controlbg.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.conserveit.controlbg.entity.Room;
import com.conserveit.controlbg.mapper.RoomMapper;
import com.conserveit.controlbg.service.RoomService;
import com.conserveit.controlbg.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateRoomCurrentTemp(String roomId, String newTempStr) {
        // Assume that the current temperature as manually input
        // Real implementation should be done by reading the temperature from the sensor

        Room room = this.getById(roomId);

        if (ObjectUtils.isEmpty(room)) {
            return R.failed("Room not found");
        }

        room.setCurrentTemperature(new BigDecimal(newTempStr));
        room.setModifiedTime(LocalDateTime.now());
        this.updateById(room);

        return R.ok("Update room temperature successfully");
    }

}
