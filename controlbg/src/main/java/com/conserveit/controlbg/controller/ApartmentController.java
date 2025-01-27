package com.conserveit.controlbg.controller;

import com.conserveit.controlbg.service.ApartmentService;
import com.conserveit.controlbg.utils.R;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/apartment")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @PutMapping("/updateCurTemp")
    public R updateCurrentTemperature(@Param("apartmentId") String apartmentId,
                                      @Param("newTempStr") String newTempStr) {
        return apartmentService.updateApartmentCurrentTemp(apartmentId, newTempStr);
    }

}
