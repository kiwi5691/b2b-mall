package b2b.mall.sso.controller;

import com.b2b.dubbo.sso.service.UserService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.common.util.CookieUtils;
import com.b2b.mall.db.model.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference(version = "${dubbo.version}")
    private UserService userService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;


    @PostMapping("/register")
    @ResponseBody
    public Result register(TbUser tbUser) {
        return userService.register(tbUser);
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        Result result = userService.login(username, password);
        // Token写入cookie 浏览器关闭就过期
        if (result.getStatus() == 200) {
            String token = result.getData().toString();
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }
        return result;
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        Result result = userService.getUserByToken(token);
        //跨域json
        if (StringUtils.isNotBlank(callback)) {
            return "callback="+result;
        }
        return result;
    }

}
