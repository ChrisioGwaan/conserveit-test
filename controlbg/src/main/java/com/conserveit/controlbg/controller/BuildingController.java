package com.conserveit.controlbg.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.conserveit.controlbg.dto.NewBuildingDTO;
import com.conserveit.controlbg.service.BuildingService;
import com.conserveit.controlbg.utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/building")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping("/create")
    public R createBuilding(@RequestBody(required = false) NewBuildingDTO newBuildingDTO) {

        if (ObjectUtils.isEmpty(newBuildingDTO)) {
            return R.failed("The building information is empty");
        }

        return buildingService.createBuilding(newBuildingDTO);
    }

}
