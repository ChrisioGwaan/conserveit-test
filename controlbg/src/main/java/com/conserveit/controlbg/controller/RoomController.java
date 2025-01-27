package com.conserveit.controlbg.controller;

import com.conserveit.controlbg.service.RoomService;
import com.conserveit.controlbg.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PutMapping("/updateCurTemp")
    public R updateCurrentTemperature(@RequestParam("roomId") String roomId,
                                      @RequestParam("newTempStr") String newTempStr) {
        return roomService.updateRoomCurrentTemp(roomId, newTempStr);
    }

}
