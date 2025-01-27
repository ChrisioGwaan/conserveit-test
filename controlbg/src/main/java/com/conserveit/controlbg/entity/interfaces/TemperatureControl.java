package com.conserveit.controlbg.entity.interfaces;

import java.math.BigDecimal;

public interface TemperatureControl {
    BigDecimal getCurrentTemperature();
    BigDecimal getTargetTemperature();
    void setCurrentTemperature(BigDecimal newCurrentTemperature);
    void setTargetTemperature(BigDecimal newTargetTemperature);
    void setIsCooling(Character coolingStatus);
    void setIsHeating(Character heatingStatus);
}
