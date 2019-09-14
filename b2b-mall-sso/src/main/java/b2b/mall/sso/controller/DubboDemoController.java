package b2b.mall.sso.controller;

import com.b2b.dubbo.sso.service.IDubboDemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther kiwi
 * @Date 2019/9/13 14:44
 */
@RestController
public class DubboDemoController {

    @Reference(version = "${dubbo.version}")
    public IDubboDemoService dubboDemoService;

    @RequestMapping("/test.do")
    @ResponseBody
    public String test() {
        return dubboDemoService.helloDubbo();
    }
}
