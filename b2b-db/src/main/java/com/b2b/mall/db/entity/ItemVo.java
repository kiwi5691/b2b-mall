package com.b2b.mall.db.entity;

import com.b2b.mall.db.model.Item;

/**
 * @auther kiwi
 * @Date 2019/9/18 10:16
 */
public class ItemVo extends Item {
    public String[] getImages() {
        String image1 = this.getImage();
        if (image1 != null && !"".equals(image1)) {
            String[] images = image1.split(",");
            return images;
        }
        return null;
    }

    public ItemVo() {
    }

    public ItemVo(Item tbItem) {
        this.setBarcode(tbItem.getBarcode());
        this.setCid(tbItem.getCid());
        this.setCreated(tbItem.getCreated());
        this.setId(tbItem.getId());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setStatus(tbItem.getStatus());
        this.setTitle(tbItem.getTitle());
        this.setUpdated(tbItem.getUpdated());
    }
}
