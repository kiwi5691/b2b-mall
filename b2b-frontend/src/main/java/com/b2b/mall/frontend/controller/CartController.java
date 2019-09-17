package com.b2b.mall.frontend.controller;

import com.b2b.dubbo.cart.service.CartService;
import com.b2b.dubbo.item.service.ItemService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.common.util.CookieUtils;
import com.b2b.mall.common.util.JsonUtils;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Value("${E3_CART}")
    private String E3_CART;
    @Value("${CART_EXPIRE}")
    private Integer CART_EXPIRE;

    @Reference
    private ItemService itemService;
    @Reference
    private CartService cartService;

    /**
     * 添加购物车：登录时添加redis，未登录添加cookie
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/add/{itemId}.html")
    public String addCartItem(@PathVariable Integer itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        // 登录添加至redis
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.addCart(user.getId(), itemId, num);
            return "cartSuccess";
        }
        // 未登录添加至cookie
        // 1、从cookie中查询商品列表。
        List<Item> cartList = getCartListFromCookie(request);
        // 2、判断商品在商品列表中是否存在。
        boolean hasItem = false;
        for (Item tbItem : cartList) {
            // 对象比较的是地址，应该是值的比较
            if (tbItem.getId() == itemId.longValue()) {
                // 3、如果存在，商品数量相加。
                tbItem.setNum(tbItem.getNum() + num);
                hasItem = true;
                break;
            }
        }
        if (!hasItem) {
            // 4、不存在，根据商品id查询商品信息。
            Item tbItem = itemService.getItemById(itemId);
            // 取一张图片
            String image = tbItem.getImage();
            if (StringUtils.isNoneBlank(image)) {
                String[] images = image.split(",");
                tbItem.setImage(images[0]);
            }
            // 设置购买商品数量
            tbItem.setNum(num);
            // 5、把商品添加到购车列表。
            cartList.add(tbItem);
        }
        // 6、把购车商品列表写入cookie。
        CookieUtils.setCookie(request, response, E3_CART, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
        return "cartSuccess";
    }

    /**
     * 从cookie中取购物车列表
     * <p>Title: getCartList</p>
     * <p>Description: </p>
     *
     * @param request
     * @return
     */
    private List<Item> getCartListFromCookie(HttpServletRequest request) {
        //取购物车列表
        String json = CookieUtils.getCookieValue(request, E3_CART, true);
        //判断json是否为null
        if (StringUtils.isNotBlank(json)) {
            //把json转换成商品列表返回
            List<Item> list = JsonUtils.jsonToList(json, Item.class);
            return list;
        }
        return new ArrayList<>();
    }

    @RequestMapping("/cart.html")
    public String showCartList(HttpServletRequest request, HttpServletResponse response) {
        //取Cookie购物车商品列表
        List<Item> cartList = getCartListFromCookie(request);
        // 登录添加至redis
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            // 合并购物车
            cartService.mergeCart(user.getId(), cartList);
            // 删除Cookie中购物车列表
            CookieUtils.deleteCookie(request, response, E3_CART);
            cartList = cartService.getCartList(user.getId());
        }
        //传递给页面
        request.setAttribute("cartList", cartList);
        return "cart";
    }

    /**
     * 更新购物车列表
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/update/num/{itemId}/{num}.action")
    @ResponseBody
    public Result updateNum(@PathVariable Integer itemId, @PathVariable Integer num,
                            HttpServletRequest request, HttpServletResponse response) {
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            return cartService.updateCartNum(user.getId(), itemId, num);
        }
        // 未登录
        // 1、接收两个参数
        // 2、从cookie中取商品列表
        List<Item> cartList = getCartListFromCookie(request);
        // 3、遍历商品列表找到对应商品
        for (Item tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 4、更新商品数量
                tbItem.setNum(num);
            }
        }
        // 5、把商品列表写入cookie。
        CookieUtils.setCookie(request, response, E3_CART, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
        // 6、响应e3Result。Json数据。
        return Result.ok();
    }

    @RequestMapping("/delete/{itemId}.html")
    public String deleteCartItem(@PathVariable Integer itemId, HttpServletRequest request,
                                 HttpServletResponse response) {
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }
        // 未登录
        // 1、从url中取商品id
        // 2、从cookie中取购物车商品列表
        List<Item> cartList = getCartListFromCookie(request);
        // 3、遍历列表找到对应的商品
        for (Item tbItem : cartList) {
            if (tbItem.getId() == itemId.longValue()) {
                // 4、删除商品。
                cartList.remove(tbItem);
                break;
            }
        }
        // 5、把商品列表写入cookie。
        CookieUtils.setCookie(request, response, E3_CART, JsonUtils.objectToJson(cartList), CART_EXPIRE, true);
        // 6、返回逻辑视图：在逻辑视图中做redirect跳转。
        return "redirect:/cart/cart.html";
    }
}
