package com.b2b.dubbo.cart.service;

import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.model.Item;

import java.util.List;

public interface CartService {
    Result addCart(Long userId, Integer itemId, int num);
    Result mergeCart(Long userId, List<Item> cookieItemList);
    List<Item> getCartList(Long userId);
    Result updateCartNum(Long userId, Integer itemId, int num);
    Result deleteCartItem(Long userId, Integer itemId);
    Result clearCartList(Long userId);
}
