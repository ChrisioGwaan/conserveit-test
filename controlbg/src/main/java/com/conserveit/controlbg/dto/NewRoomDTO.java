package com.conserveit.controlbg.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewRoomDTO {

    private String apartmentId;

    private BigDecimal temperature;

    private Character isHeating;

    private Character isCooling;

}
