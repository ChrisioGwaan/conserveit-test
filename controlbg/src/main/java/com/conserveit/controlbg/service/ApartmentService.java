package com.conserveit.controlbg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.conserveit.controlbg.entity.Apartment;
import com.conserveit.controlbg.utils.R;

public interface ApartmentService extends IService<Apartment> {

    // Need a schedule to update the temperature from sensor
    R updateApartmentCurrentTemp(String apartmentId, String newTempStr);

}
