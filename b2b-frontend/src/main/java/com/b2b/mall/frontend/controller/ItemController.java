package com.b2b.mall.frontend.controller;

import com.b2b.dubbo.item.service.ItemService;
import com.b2b.mall.db.entity.ItemVo;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ItemDesc;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

	@Reference(version = "${dubbo.version}")
    private ItemService itemService;
	
	@RequestMapping("/item/{itemId}.html")
	public String showItemInfo(@PathVariable Integer itemId, Model model) {
		//跟据商品id查询商品信息
		Item tbItem = itemService.getItemById(itemId);
		//把TbItem转换成Item对象
		ItemVo item = new ItemVo(tbItem);
		//根据商品id查询商品描述
		ItemDesc itemDesc = itemService.getItemDescById(itemId);
		//把数据传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}
