package com.b2b.mall.admin.controller.error;
import com.b2b.mall.common.enums.CommonParamsEnum;
import org.junit.rules.ErrorCollector;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kiwi
 * @Date 2019/6/26 14:30
 */
public class ErrorController  extends ErrorCollector{
    private static final String ERROR_PATH = "/error";

    /**
     * 渲染404，500
     *
     * @param request request
     * @return String
     */
    @GetMapping(value = ERROR_PATH)
    public String handleError(HttpServletRequest request) {
        final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode.equals(CommonParamsEnum.NOT_FOUND.getValue())) {
            return "redirect:/404";
        } else {
            return "redirect:/500";
        }
    }

    /**
     * 渲染404页面
     *
     * @return String
     */
    @GetMapping(value = "/404")
    public String fourZeroFour() {
        return "error/404";
    }

    /**
     * 渲染500页面
     *
     * @return String

     @GetMapping(value = "/500")
     public String fiveZeroZero() {
     return "common/error/500";
     }*/


}
