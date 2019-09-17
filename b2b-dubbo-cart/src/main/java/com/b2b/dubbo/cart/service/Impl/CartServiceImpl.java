package com.b2b.dubbo.cart.service.Impl;

import com.b2b.dubbo.cart.service.CartService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.mapper.ItemMapper;
import com.b2b.mall.db.model.Item;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车处理服务
 */
@Service(version = "${Dubbo_Version}")
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ItemMapper itemMapper;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Override
    public Result addCart(Long userId, Integer itemId, int num) {
        Boolean hasItem = redisTemplate.opsForHash().hasKey(REDIS_CART_PRE + ":" + userId, itemId+"");
        if (hasItem) {
            // 商品存在，数量相加
            Item item = (Item) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId+"");
            item.setNum(item.getNum() + num);
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", item);
            return Result.ok();
        } else {
            // 商品不存在，查询数据库添加商品
            Item item = itemMapper.selectByPrimaryKey(itemId);
            item.setNum(num);
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                item.setImage(image.split(",")[0]);
            }
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", item);
            return Result.ok();
        }
    }

    @Override
    public Result mergeCart(Long userId, List<Item> cookieItemList) {
        for (Item item : cookieItemList) {
            addCart(userId, item.getId(), item.getNum());
        }
        return Result.ok();
    }

    @Override
    public List<Item> getCartList(Long userId) {
        List<Object> results = redisTemplate.opsForHash().values(REDIS_CART_PRE + ":" + userId);
        List<Item> tbItems = new ArrayList<>();
        for (Object result : results) {
            tbItems.add((Item) result);
        }
        return tbItems;
    }

    @Override
    public Result updateCartNum(Long userId, Integer itemId, int num) {
        Item tbItem = (Item) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId+"");
        tbItem.setNum(tbItem.getNum() + num);
        redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", tbItem);
        return Result.ok();
    }

    @Override
    public Result deleteCartItem(Long userId, Integer itemId) {
        redisTemplate.opsForHash().delete(REDIS_CART_PRE + ":" + userId, itemId+"");
        return Result.ok();
    }

    /**
     * 清除购物车
     * @param userId
     * @return
     */
    @Override
    public Result clearCartList(Long userId) {
        redisTemplate.delete(REDIS_CART_PRE + ":" + userId);
        return Result.ok();
    }
}
