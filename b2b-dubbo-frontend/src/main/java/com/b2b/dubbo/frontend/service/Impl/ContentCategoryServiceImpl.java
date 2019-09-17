package com.b2b.dubbo.frontend.service.Impl;

import com.b2b.dubbo.frontend.service.ContentCategoryService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.BaseUIDTO;
import com.b2b.mall.db.mapper.ContentCategoryMapper;
import com.b2b.mall.db.model.ContentCategory;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public List<BaseUIDTO> getContentCategoryList(Long parentId) {
        // 1、取查询参数id，parentId
        // 2、根据parentId查询tb_content_category，查询子节点列表。
        // 3、得到List<TbContentCategory>
        List<ContentCategory> list = contentCategoryMapper.selectContentCatsByParentId(parentId);
        // 4、把列表转换成List<EasyUITreeNode>ub
        List<BaseUIDTO> resultList = new ArrayList<>();
        for (ContentCategory contentCategory : list) {
            BaseUIDTO contentCategoryDTO = new BaseUIDTO();
            contentCategoryDTO.setId(contentCategory.getId());
            contentCategoryDTO.setText(contentCategory.getName());
            contentCategoryDTO.setState(contentCategory.getIsParent()?"closed":"open");
            // 添加到列表
            resultList.add(contentCategoryDTO);
        }
//        list.forEach(list1 -> resultList.forEach(contentCategoryDTO -> {
//            contentCategoryDTO.setId(list1.getId());
//            contentCategoryDTO.setText(list1.getName());
//            contentCategoryDTO.setState(list1.getIsParent()?"closed":"open");
//        }));
        return resultList;
    }

    @Override
    public Result addContentCategory(long parentId, String name) {
        // 1、接收两个参数：parentId、name
        // 2、向tb_content_category表中插入数据。
        // a)创建一个TbContentCategory对象
        ContentCategory tbContentCategory = new ContentCategory();
        // b)补全TbContentCategory对象的属性
        tbContentCategory.setIsParent(false);
        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        tbContentCategory.setSortOrder(1);
        //状态。可选值:1(正常),2(删除)
        tbContentCategory.setStatus(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        // c)向tb_content_category表中插入数据
        contentCategoryMapper.insertCategory(tbContentCategory);
        // 3、判断父节点的isparent是否为true，不是true需要改为true。
        ContentCategory parentNode = contentCategoryMapper.selectContentCatById(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateContentCategoryById(parentNode);
        }
        // 4、需要主键返回。
        // 5、返回E3Result，其中包装TbContentCategory对象
        return Result.ok(tbContentCategory);
    }
}
