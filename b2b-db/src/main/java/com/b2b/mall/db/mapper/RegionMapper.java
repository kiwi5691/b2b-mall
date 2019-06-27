package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Region;
import com.b2b.mall.db.model.RegionExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface RegionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    long countByExample(RegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int deleteByExample(RegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int insert(Region record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int insertSelective(Region record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Region selectOneByExample(RegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Region selectOneByExampleSelective(@Param("example") RegionExample example, @Param("selective") Region.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<Region> selectByExampleSelective(@Param("example") RegionExample example, @Param("selective") Region.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    List<Region> selectByExample(RegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Region selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Region.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    Region selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Region record, @Param("example") RegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Region record, @Param("example") RegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Region record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_region
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Region record);
}