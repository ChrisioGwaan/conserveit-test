package com.conserveit.controlbg.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.conserveit.controlbg.entity.Apartment;
import com.conserveit.controlbg.mapper.ApartmentMapper;
import com.conserveit.controlbg.service.ApartmentService;
import com.conserveit.controlbg.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ApartmentServiceImpl extends ServiceImpl<ApartmentMapper, Apartment> implements ApartmentService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateApartmentCurrentTemp(String apartmentId, String newTempStr) {
        // Assume that the current temperature as manually input
        // Real implementation should be done by reading the temperature from the sensor

        Apartment apartment = this.getById(apartmentId);

        if (ObjectUtils.isEmpty(apartment)) {
            return R.failed("Apartment not found");
        }

        apartment.setCurrentTemperature(new BigDecimal(newTempStr));
        apartment.setModifiedTime(LocalDateTime.now());
        this.updateById(apartment);

        return R.ok("Update apartment temperature successfully");
    }

}
