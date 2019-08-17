package com.b2b.mall.common.service;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/8/12 17:40
 */
public interface JobService {
    void delBeforeLog();
    List<?> selBeforeLog();
    void delBeforeOpLog();
    List<?> selBeforeOpLog();

}
