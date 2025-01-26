package com.conserveit.controlbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "apartment_owner")
public class ApartmentOwner {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long apartmentId;

    private String firstName;

    private String lastName;

    private LocalDateTime createTime;

    private LocalDateTime modifiedTime;

    private Character isDel;

}
