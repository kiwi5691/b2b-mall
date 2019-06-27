package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.RegionService;
import com.b2b.mall.db.mapper.RegionMapper;
import com.b2b.mall.db.model.Region;
import com.b2b.mall.db.model.RegionExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/6/27 22:40
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Resource
    private RegionMapper regionMapper;

    private static List<Region> Regions;

    public List<Region> getRegions() {
        if(Regions==null){
            createRegion();
        }
        return Regions;
    }

    private synchronized void createRegion(){
        if (Regions == null) {
            Regions = getAll();
        }
    }

    public List<Region> getAll(){
        RegionExample example = new RegionExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return regionMapper.selectByExample(example);
    }

    public List<Region> queryByPid(Integer parentId) {
        RegionExample example = new RegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public Region findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<Region> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        RegionExample example = new RegionExample();
        RegionExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }


    public List<Region> queryChildren(Integer id) {
        RegionExample example = new RegionExample();
        example.or().andPidEqualTo(id);
        return regionMapper.selectByExample(example);
    }
}
