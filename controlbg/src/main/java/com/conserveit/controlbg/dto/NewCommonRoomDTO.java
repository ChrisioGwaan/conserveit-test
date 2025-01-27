package com.conserveit.controlbg.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewCommonRoomDTO {

    private String buildingId;

    private Integer type;

    private BigDecimal temperature;

    private Character isHeating;

    private Character isCooling;

}
