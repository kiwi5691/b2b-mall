package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Region;
import com.b2b.mall.db.model.RegionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface RegionMapper {

    long countByExample(RegionExample example);


    int deleteByExample(RegionExample example);


    int deleteByPrimaryKey(Integer id);


    int insert(Region record);


    int insertSelective(Region record);


    Region selectOneByExample(RegionExample example);


    Region selectOneByExampleSelective(@Param("example") RegionExample example, @Param("selective") Region.Column... selective);


    List<Region> selectByExampleSelective(@Param("example") RegionExample example, @Param("selective") Region.Column... selective);


    List<Region> selectByExample(RegionExample example);


    Region selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Region.Column... selective);


    Region selectByPrimaryKey(Integer id);


    int updateByExampleSelective(@Param("record") Region record, @Param("example") RegionExample example);


    int updateByExample(@Param("record") Region record, @Param("example") RegionExample example);


    int updateByPrimaryKeySelective(Region record);


    int updateByPrimaryKey(Region record);
}