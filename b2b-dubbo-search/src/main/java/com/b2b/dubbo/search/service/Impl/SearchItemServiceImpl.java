package com.b2b.dubbo.search.service.Impl;

import com.b2b.dubbo.search.service.SearchItemService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.SearchItem;
import com.b2b.mall.db.mapper.SearchItemMapper;
import org.apache.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${Dubbo_Version}")
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper itemMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public Result importItems() {
        try {
            //查询商品列表
            List<SearchItem> itemList = itemMapper.getItemList();
            //导入索引库
            for (SearchItem searchItem : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                //写入索引库
                solrClient.add(document);
            }
            //提交
            solrClient.commit();
            //返回成功
            return Result.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(500, "商品导入失败");
        }
    }
}
