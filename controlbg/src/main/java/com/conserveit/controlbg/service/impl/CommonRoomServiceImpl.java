package com.conserveit.controlbg.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.conserveit.controlbg.entity.CommonRoom;
import com.conserveit.controlbg.mapper.CommonRoomMapper;
import com.conserveit.controlbg.service.CommonRoomService;
import com.conserveit.controlbg.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CommonRoomServiceImpl extends ServiceImpl<CommonRoomMapper, CommonRoom> implements CommonRoomService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateCurrentTemperature(String commonRoomId, String newTempStr) {
        // Assume that the current temperature as manually input
        // Real implementation should be done by reading the temperature from the sensor

        CommonRoom commonRoom = this.getById(commonRoomId);

        if (ObjectUtils.isEmpty(commonRoom)) {
            return R.failed("Common room not found");
        }

        commonRoom.setCurrentTemperature(new BigDecimal(newTempStr));
        commonRoom.setModifiedTime(LocalDateTime.now());
        this.updateById(commonRoom);

        return R.ok("Update common room temperature successfully");
    }

}
