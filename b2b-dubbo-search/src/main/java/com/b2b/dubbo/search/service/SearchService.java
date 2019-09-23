package com.b2b.dubbo.search.service;


import com.b2b.mall.db.entity.SearchResult;

public interface SearchService {
    SearchResult search(String keyWord, int page, int rows) throws Exception;
    SearchResult searchCategory(String keyWord, int page, int rows) throws Exception;
}
