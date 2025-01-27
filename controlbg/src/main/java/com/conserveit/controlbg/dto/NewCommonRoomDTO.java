package com.conserveit.controlbg.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewCommonRoomDTO {

    private String buildingId;

    private Integer type;

    private BigDecimal targetTemperature;

    private Character isHeating;

    private Character isCooling;

}
