package b2b.mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kiwi
 */
@Controller
@RequestMapping("/page")
public class PageController {


    @RequestMapping("/my-account")
    public String showMyAccount() {
        return "my-account";
    }

}
