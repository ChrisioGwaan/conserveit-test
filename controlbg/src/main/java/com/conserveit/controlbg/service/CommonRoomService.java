package com.conserveit.controlbg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.conserveit.controlbg.entity.CommonRoom;
import com.conserveit.controlbg.utils.R;

public interface CommonRoomService extends IService<CommonRoom> {

    // Need a schedule to update the temperature from sensor
    R updateCurrentTemperature(String commonRoomId, String newTempStr);

}
