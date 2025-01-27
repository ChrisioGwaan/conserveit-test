package com.conserveit.controlbg.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class NewApartmentDTO {

    public NewApartmentDTO() {
        apartmentOwners = new ArrayList<>();
        rooms = new ArrayList<>();
    }

    private String buildingId;

    private Integer roomNumber;

    private BigDecimal temperature;

    private Character isHeating;

    private Character isCooling;

    private ArrayList<NewApartmentOwnerDTO> apartmentOwners;

    private ArrayList<NewRoomDTO> rooms;

}
