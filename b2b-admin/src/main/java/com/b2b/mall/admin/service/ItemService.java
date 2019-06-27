package com.b2b.mall.admin.service;

import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ResObject;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author kiwi
 * @Date: 2019/6/26 12:51
 */
public interface ItemService {
    void itemManage(Item item, Integer pageCurrent, Integer pageSize, Integer pageCount, Model model);
    void postItemExcel(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void itemEditGet(Model model, Item item);
    void itemEditPost(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file, Item item, HttpSession httpSession);
    ResponseEntity<?> getFile();
    ResObject<Object> itemEditState(Item item1);
}
