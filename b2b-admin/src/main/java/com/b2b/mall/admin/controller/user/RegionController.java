package com.b2b.mall.admin.controller.user;

import com.b2b.mall.admin.service.Impl.RegionServiceImpl;
import com.b2b.mall.admin.service.OrderService;
import com.b2b.mall.admin.service.RegionService;
import com.b2b.mall.db.mapper.RegionMapper;
import com.b2b.mall.db.model.Order;
import com.b2b.mall.db.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/6/27 22:20
 */
@RestController
public class RegionController {
    @Autowired
    private  RegionServiceImpl regionService;



    @GetMapping("/region")
    public List<Region> getAllRegion() {
        return regionService.getRegions();
    }

}
