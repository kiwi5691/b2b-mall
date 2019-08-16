package com.b2b.mall.admin.controller.monitor;


import com.b2b.mall.admin.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author kiwi
 */
@Slf4j
@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    private ILogService logService;

//    @GetMapping("list")
//    @RequiresPermissions("log:view")
//    public FebsResponse logList(Log log, QueryRequest request) {
//        Map<String, Object> dataTable = getDataTable(this.logService.findLogs(log, request));
//        return new FebsResponse().success().data(dataTable);
//    }
//
//    @GetMapping("delete/{ids}")
//    @RequiresPermissions("log:delete")
//    public FebsResponse deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) throws FebsException {
//        try {
//            String[] logIds = ids.split(StringPool.COMMA);
//            this.logService.deleteLogs(logIds);
//            return new FebsResponse().success();
//        } catch (Exception e) {
//            String message = "删除日志失败";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
//    }
//
//    @GetMapping("excel")
//    @RequiresPermissions("log:export")
//    public void export(QueryRequest request, Log lg, HttpServletResponse response) throws FebsException {
//        try {
//            List<Log> logs = this.logService.findLogs(lg, request).getRecords();
//            ExcelKit.$Export(Log.class, response).downXlsx(logs, false);
//        } catch (Exception e) {
//            String message = "导出Excel失败";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
//    }
}
