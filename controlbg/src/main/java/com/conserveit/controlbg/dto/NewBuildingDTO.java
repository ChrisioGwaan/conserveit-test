package com.conserveit.controlbg.dto;

import com.conserveit.controlbg.entity.ApartmentOwner;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class NewBuildingDTO {

    public NewBuildingDTO() {
        apartments = new ArrayList<>();
        commonRooms = new ArrayList<>();
    }

    private BigDecimal targetTemperature;

    private Integer numberOfApartment;

    private Integer numberOfCommonRoom;

    private ArrayList<NewApartmentDTO> apartments;

    private ArrayList<NewCommonRoomDTO> commonRooms;

}
