package com.conserveit.controlbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "building")
public class Building {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private BigDecimal temperature;

    private Integer numberOfApartment;

    private Integer numberOfCommonRoom;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;

    private Character isDel;

}
