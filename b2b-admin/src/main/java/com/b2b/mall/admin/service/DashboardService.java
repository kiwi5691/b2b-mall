package com.b2b.mall.admin.service;

import com.b2b.mall.db.model.Delivery;
import com.b2b.mall.db.model.Stats;
import org.springframework.ui.Model;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:50
 */
public interface DashboardService {
    void dashboardInit(Model model, Stats stats);
}
