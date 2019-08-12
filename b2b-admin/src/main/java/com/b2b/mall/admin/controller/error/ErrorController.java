package com.b2b.mall.admin.controller.error;
import com.b2b.mall.common.enums.CommonParamsEnum;
import com.b2b.mall.common.enums.IStatusMessage;
import org.junit.rules.ErrorCollector;
import org.springframework.http.HttpStatus;
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
    public String handleError(HttpServletRequest request,HttpStatus httpStatus) {
        final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode.equals(IStatusMessage.SystemStatus.NOT_FOUND.getCode())) {
            return "redirect:/404";
        }
        else if (statusCode.equals(IStatusMessage.SystemStatus.UN_AUTHENTICATION.getCode())){
            return "redirect:/403";
        }
        else{
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
     * 渲染404页面
     *
     * @return String
     */
    @GetMapping(value = "/403")
    public String fourZeroThree() {
        return "error/403";
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
