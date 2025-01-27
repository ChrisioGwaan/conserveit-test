package com.conserveit.controlbg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.conserveit.controlbg.dto.NewBuildingDTO;
import com.conserveit.controlbg.entity.Building;
import com.conserveit.controlbg.utils.R;

public interface BuildingService extends IService<Building> {

    R createBuilding(NewBuildingDTO newBuildingDTO);

    R updateBuilding();

    R searchAllBuilding();

    R deleteBuilding();

}
