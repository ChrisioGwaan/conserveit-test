package com.conserveit.controlbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.conserveit.controlbg.entity.interfaces.TemperatureControl;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "common_room")
public class CommonRoom implements TemperatureControl {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long buildingId;

    private Integer type;

    private BigDecimal temperature;

    private Character isHeating;

    private Character isCooling;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;

    private Character isDel;

}
