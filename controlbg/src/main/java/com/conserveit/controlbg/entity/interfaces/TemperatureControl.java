package com.conserveit.controlbg.entity.interfaces;

import java.math.BigDecimal;

public interface TemperatureControl {
    BigDecimal getTemperature();
    void setTemperature(BigDecimal temperature);
    void setIsCooling(Character coolingStatus);
    void setIsHeating(Character heatingStatus);
}
