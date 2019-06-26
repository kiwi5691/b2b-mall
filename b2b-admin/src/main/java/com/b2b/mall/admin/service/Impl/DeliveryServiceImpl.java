package com.b2b.mall.admin.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b2b.mall.admin.service.DeliveryService;
import com.b2b.mall.common.util.HttpRequest;
import com.b2b.mall.db.mapper.DeliveryMapper;
import com.b2b.mall.db.model.Delivery;
import com.b2b.mall.db.model.Express;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kiwi
 * @Date  2019/6/26 12:54
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {
    @Resource
    private DeliveryMapper deliveryMapper;

    //快递id
    private int id ;

    @Override
    public void deliveryManage(Delivery delivery, Model model) {
        List<Delivery> list = deliveryMapper.selectAll();
        model.addAttribute("list", list);
    }

    @Override
    public void searchGet(Model model, Delivery delivery) {
        id = delivery.getId();
        Delivery delivery1 = deliveryMapper.selectByPrimaryKey(id);
        model.addAttribute("delivery", delivery1);
    }

    @Override
    public void searchPost(Model model, Delivery delivery) {

        Delivery delivery1 = deliveryMapper.selectByPrimaryKey(id);
        delivery1.setExpressNo(delivery.getExpressNo());
        String code = delivery1.getDeliveryCode();
        String expressNo = delivery.getExpressNo();
        JSONArray result = getExpress100(code, expressNo);
        List<Express> list = new ArrayList<>();
        for (int j = 0; j < result.size() ; j++) {
            JSONObject object = result.getJSONObject(j);
            Express e = new Express();
            e.setId(j+1);
            e.setContext(object.getString("context"));
            e.setLocation(object.getString("location"));
            e.setTime(object.getString("time"));
            list.add(e);
        }
        model.addAttribute("list", list);
        model.addAttribute("delivery", delivery1);
    }

    @Override
    public JSONArray getExpress100(String deliveryCode, String expressNo) {
        //根据物流公司中文名去查询其公司编号
        StringBuilder url = new StringBuilder("https://m.kuaidi100.com/query?");
        url.append("type=").append(deliveryCode).append("&").append("postid=").append(expressNo);
        String content = HttpRequest.readData(url.toString(), "POST");
        JSONObject responseJson = JSONObject.parseObject(content);
        JSONArray result = responseJson.getJSONArray("data");
        return result;
    }

    @Override
    public void deliveryEditPost(Model model, Delivery delivery) {
        deliveryMapper.insert(delivery);
    }

    @Override
    public void deliveryDeleteStatePost(Model model, Delivery delivery) {
        deliveryMapper.deleteByDeliveryName(delivery.getDeliveryName());
    }
}
