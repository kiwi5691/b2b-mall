package com.b2b.mall.admin.service.Impl;

import com.b2b.mall.admin.service.ItemService;
import com.b2b.mall.common.redis.RedisManager;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.ItemCategoryMapper;
import com.b2b.mall.db.mapper.ItemMapper;
import com.b2b.mall.db.mapper.ReItemMapper;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ItemCategory;
import com.b2b.mall.db.model.ReItem;
import com.b2b.mall.db.model.ResObject;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 13:08
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Resource
    private ItemCategoryMapper itemCategoryMapper;

    @Resource
    private ReItemMapper reItemMapper;

    @Resource
    private  ResourceLoader resourceLoader;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private RedisManager redisManager;

    public static final String ROOT = "src/main/resources/static/img/item/";

    MongoUtil mongoUtil = new MongoUtil();

    List<Item> itemList;

    File getFile = null;

    @Override
    public void itemManage(Item item, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model) {
        if (pageSize == 0) {
            pageSize = 50;
        }
        if (pageCurrent == 0) {
            pageCurrent = 1;
        }

        int rows = itemMapper.count(item);
        if (pageCount == 0) {
            pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        }
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        itemList = itemMapper.list(item);
        for (Item i : itemList) {
            i.setUpdatedStr(DateUtil.getDateStr(i.getUpdated()));
        }
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setStart(0);
        itemCategory.setEnd(Integer.MAX_VALUE);

        List<ItemCategory> itemCategoryList = null;

         itemCategoryList=(List<ItemCategory>)redisManager.getList("itemCategoryList");

         if(itemCategoryList==null){
             itemCategoryList=itemCategoryMapper.list(itemCategory);
             redisManager.setList("itemCategoryList",itemCategoryList);
         }

        Integer minPrice = item.getMinPrice();
        Integer maxPrice = item.getMaxPrice();
        model.addAttribute("itemCategoryList", itemCategoryList);
        model.addAttribute("itemList", itemList);
        String pageHTML = PageUtil.getPageContent("itemManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getTitle() + "&cid=" + item.getCid() + "&minPrice" + minPrice + "&maxPrice" + maxPrice, pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
    }

    @Override
    public void postItemExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //导出excel
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("id", "商品id");
        fieldMap.put("title", "商品标题");
        fieldMap.put("sellPoint", "商品卖点");
        fieldMap.put("price", "商品价格");
        fieldMap.put("num", "库存数量");
        fieldMap.put("image", "商品图片");
        fieldMap.put("cid", "所属类目，叶子类目");
        fieldMap.put("status", "商品状态，1-正常，2-下架，3-删除");
        fieldMap.put("created", "创建时间");
        fieldMap.put("updated", "更新时间");
        String sheetName = "商品管理报表";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=ItemManage.xls");//默认Excel名称
        response.flushBuffer();
        OutputStream fos = response.getOutputStream();
        try {
            ExcelUtil.listToExcel(itemList, fieldMap, sheetName, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    String imageName = null;

    @Override
    public void itemEditGet(Model model, Item item) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setStart(0);
        itemCategory.setEnd(Integer.MAX_VALUE);
        List<ItemCategory> itemCategoryList = itemCategoryMapper.list(itemCategory);
        model.addAttribute("itemCategoryList", itemCategoryList);
        if (item.getId() != 0) {
            Item item1 = itemMapper.findById(item);
            String id = String.valueOf(item.getId());
            GridFSDBFile fileById = mongoUtil.getFileById(id);
            if (fileById != null) {
                StringBuilder sb = new StringBuilder(ROOT);
                imageName = fileById.getFilename();
                sb.append(imageName);
                try {
                    getFile = new File(sb.toString());
                    fileById.writeTo(getFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                item1.setImage(imageName);
            }
            model.addAttribute("item", item1);
        }

    }

    @Override
    public void itemEditPost(Model model, HttpServletRequest request, MultipartFile file, Item item, HttpSession httpSession) {
        //根据时间和随机数生成id
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setBarcode("");
        item.setImage("");
        int rannum = 0;
        if (file.isEmpty()) {
            System.out.println("图片未上传");
        } else {
            try {
                Path path = Paths.get(ROOT, file.getOriginalFilename());
                File tempFile = new File(path.toString());
                if (!tempFile.exists()) {
                    Files.copy(file.getInputStream(), path);
                }
                LinkedHashMap<String, Object> metaMap = new LinkedHashMap<String, Object>();
                String id = null;
                if (item.getId() != 0) {
                    id = String.valueOf(item.getId());
                } else {
                    Random random = new Random();
                    // 获取5位随机数
                    rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 1000;
                    id = String.valueOf(rannum);
                }
                metaMap.put("contentType", "jpg");
                metaMap.put("_id", id);
                mongoUtil.uploadFile(tempFile, id, metaMap);
                tempFile.delete();
                getFile.delete();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("get File by Id Success");
        }

        if (item.getId() != 0) {
            itemMapper.update(item);
        } else {
            item.setId(rannum);
            itemMapper.insert(item);
//            ActiveMQTopic itemAddTopic = new ActiveMQTopic("itemAddTopic");
//            jmsMessagingTemplate.convertAndSend(itemAddTopic, item.getId());
        }

    }

    @Override
    public ResponseEntity<?> getFile() {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, imageName).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResObject<Object> itemEditState(Item item1) {
        Item item = itemMapper.findById(item1);
        ReItem reItem = new ReItem();
        reItem.setId(item.getId());
        reItem.setBarcode(item.getBarcode());
        reItem.setCid(item.getCid());
        reItem.setImage(item.getImage());
        reItem.setPrice(item.getPrice());
        reItem.setNum(item.getNum());
        reItem.setSellPoint(item.getSellPoint());
        reItem.setStatus(item.getStatus());
        reItem.setTitle(item.getTitle());
        reItem.setRecovered(new Date());
        reItemMapper.insert(reItem);
        itemMapper.delete(item1);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;

    }
}
