package com.conserveit.controlbg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conserveit.controlbg.entity.CommonRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommonRoomMapper extends BaseMapper<CommonRoom> {

    List<CommonRoom> selectAllCommonRoomByBuildingId(@Param("buildingId") String buildingId);

}
