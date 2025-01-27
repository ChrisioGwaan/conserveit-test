package com.conserveit.controlbg.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonRoomEnum {

    UNKNOWN(0, "Unknown"),

    GYM(1, "Gym"),

    LIBRARY(2, "Library"),

    LAUNDRY(3, "Laundry");

    private final Integer type;

    private final String name;

    public static CommonRoomEnum getEnum(String type){
        for (CommonRoomEnum obj : CommonRoomEnum.values()){
            if(obj.type.equals(type)){
                return obj;
            }
        }
        return null;
    }

}
