package com.conserveit.controlbg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.conserveit.controlbg.entity.Room;
import com.conserveit.controlbg.utils.R;

public interface RoomService extends IService<Room>  {

    // Need a schedule to update the temperature from sensor
    R updateRoomCurrentTemp(String roomId, String newTempStr);

}
