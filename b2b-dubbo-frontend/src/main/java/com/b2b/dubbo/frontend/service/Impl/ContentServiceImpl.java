package com.b2b.dubbo.frontend.service.Impl;

import com.b2b.dubbo.frontend.service.ContentService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.ContentDTO;
import com.b2b.mall.db.mapper.ContentMapper;
import com.b2b.mall.db.model.Content;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kiwi
 */
@Service(version = "${Dubbo_Version}")
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Override
    public ContentDTO getContentListByCategoryId(Long categoryId, int page, int rows) {
        PageHelper.startPage(page, rows);

        List<Content> tbContents = new ArrayList<>();
        if (categoryId == 0L) {
            tbContents = contentMapper.getAllContentList();
        } else {
            tbContents = contentMapper.getContentListByCategoryId(categoryId);
        }

        PageInfo<Content> pageInfo = new PageInfo<>(tbContents);
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setRows(tbContents);
        contentDTO.setTotal(pageInfo.getTotal());
        return contentDTO;
    }

    @Override
    public List<Content> getContentList(Long cid) {
        // 查询缓存
        try {
            List<Content> contents = (List<Content>) redisTemplate.opsForHash().get(CONTENT_KEY, cid.toString());
            System.out.println("read redis catch data...");
            if (!contents.isEmpty() && contents != null) {
                return contents;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据cid查询内容列表
        List<Content> list = contentMapper.getContentListByCategoryId(cid);
        // 向缓存中添加数据
        try {
            redisTemplate.opsForHash().put(CONTENT_KEY, cid.toString(), list);
            System.out.println("write redis catch data...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Result addContent(Content content) {
        //补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据
        contentMapper.insert(content);
        //缓存同步
        redisTemplate.opsForHash().delete(CONTENT_KEY, content.getCategoryId().toString());
        return Result.ok();
    }
}
