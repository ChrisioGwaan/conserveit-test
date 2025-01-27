package com.conserveit.controlbg.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewRoomDTO {

    private String apartmentId;

    private BigDecimal targetTemperature;

    private Character isHeating;

    private Character isCooling;

}
