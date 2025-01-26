package com.conserveit.controlbg.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonRoomEnum {

    GYM("0", "Gym"),

    LIBRARY("1", "Library"),

    LAUNDRY("2", "Laundry");

    private final String type;

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
