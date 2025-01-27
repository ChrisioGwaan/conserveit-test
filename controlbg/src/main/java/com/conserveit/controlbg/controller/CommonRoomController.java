package com.conserveit.controlbg.controller;

import com.conserveit.controlbg.service.CommonRoomService;
import com.conserveit.controlbg.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/common-room")
@RequiredArgsConstructor
public class CommonRoomController {

    private final CommonRoomService commonRoomService;

    @PutMapping("/updateCurTemp")
    public R updateCurrentTemperature(@RequestParam("commonRoomId") String commonRoomId,
                                      @RequestParam("newTempStr") String newTempStr) {
        return commonRoomService.updateCurrentTemperature(commonRoomId, newTempStr);
    }

}
