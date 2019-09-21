package com.b2b.dubbo.item.service.Impl;

import com.b2b.dubbo.item.service.ItemService;
import com.b2b.mall.db.entity.ContentDTO;
import com.b2b.mall.db.mapper.ItemCategoryMapper;
import com.b2b.mall.db.mapper.ItemDescMapper;
import com.b2b.mall.db.mapper.ItemMapper;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ItemDesc;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service(version = "${Dubbo_Version}")
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemCategoryMapper itemCategoryMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${ITEM_INFO_KEY}")
    private String ITEM_INFO_KEY;
    @Value("${ITEM_INFO_BASE_KEY}")
    private String ITEM_INFO_BASE_KEY;
    @Value("${ITEM_INFO_DESC_KEY}")
    private String ITEM_INFO_DESC_KEY;
    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;

    @Override
    public Item getItemById(Integer itemId) {
        // 查询缓存
        try {
            Item tbItem = (Item) redisTemplate.opsForValue().get(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_BASE_KEY);
            if (tbItem != null) {
                System.out.println("read redis item base information...");
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 查询数据库
        Item tbItem = itemMapper.selectByPrimaryKey(itemId);
        if (tbItem != null) {
            try {
                tbItem.setCategoryName(itemCategoryMapper.findNameById(tbItem.getCid()));
                // 把数据保存到缓存
                redisTemplate.opsForValue().set(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_BASE_KEY, tbItem);
                // 设置缓存的有效期
                redisTemplate.expire(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_BASE_KEY, ITEM_INFO_EXPIRE, TimeUnit.HOURS);
                System.out.println("write redis item base information...");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tbItem;
        }
        return null;
    }

    @Override
    public ItemDesc getItemDescById(Long itemId) {
        // 查询缓存
        try {
            ItemDesc itemDesc = (ItemDesc) redisTemplate.opsForValue().get(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_DESC_KEY);
            if (itemDesc != null) {
                System.out.println("read redis item desc information...");
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 查询数据库
        ItemDesc itemDesc = itemDescMapper.selectItemDescByPrimaryKey(itemId);
        if (itemDesc != null) {
            // 把数据保存到缓存
            try {
                redisTemplate.opsForValue().set(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_DESC_KEY, itemDesc);
                redisTemplate.expire(ITEM_INFO_KEY + ":" + itemId + ":" + ITEM_INFO_DESC_KEY, ITEM_INFO_EXPIRE, TimeUnit.HOURS);
                System.out.println("write redis item desc information...");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemDesc;
        }
        return null;
    }

    @Override
    public ContentDTO getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<Item> list = itemMapper.getItemList();
        //取分页信息
        PageInfo<Item> pageInfo = new PageInfo<>(list);

        //创建返回结果对象
        ContentDTO result = new ContentDTO();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);

        return result;
    }

//    // 7、发送消息队列，通知新增商品id
//    ActiveMQTopic itemAddTopic = new ActiveMQTopic("itemAddTopic");
//        jmsMessagingTemplate.convertAndSend(itemAddTopic, item.getId());

}
