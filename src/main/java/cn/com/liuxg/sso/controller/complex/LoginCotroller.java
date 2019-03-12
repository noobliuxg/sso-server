package cn.com.liuxg.sso.controller.complex;

import cn.com.liuxg.sso.utils.LoginCheck;
import cn.com.liuxg.sso.utils.RespMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sso")
public class LoginCotroller {

    @RequestMapping("/doLogin")
    @ResponseBody
    public RespMessage doLogin(String userName, String password){
        RespMessage respMessage = new RespMessage();
        respMessage.setRespCode("500");
        respMessage.setRespMsg("用户名或密码错误");
        boolean ok = LoginCheck.checkLogin(userName, password);
        if (ok){
            respMessage.setRespCode("200");
            respMessage.setRespMsg("用户名和密码正确");
            Map<String, List> args = new HashMap<>();
            List<Map> cookies = new ArrayList<>();

            // 向www.a.com服务器发送增加cookie
            Map<String,String> targetCookiea = new HashMap<>();
            String urla = "http://www.a.com/a/addCookie";
            targetCookiea.put("targetUrl", urla);
            targetCookiea.put("cookieName", LoginCheck.COOKIE_NAME);
            targetCookiea.put("cookieValue", LoginCheck.COOKIE_VALUE);

            // 向www.b.com服务器发送增加cookie
            Map<String,String> targetCookieb = new HashMap<>();
            String urlb = "http://www.b.com/b/addCookie";
            targetCookieb.put("targetUrl", urlb);
            targetCookieb.put("cookieName", LoginCheck.COOKIE_NAME);
            targetCookieb.put("cookieValue", LoginCheck.COOKIE_VALUE);
            cookies.add(targetCookiea);
            cookies.add(targetCookieb);
            args.put("targetCookies",cookies);
        }
        return respMessage;
    }

    @GetMapping("/checkCookie")
    @ResponseBody
    public RespMessage checkCookie(String cookieName,String cookieValue){
        RespMessage result = new RespMessage();
        result.setRespCode("500");
        result.setRespMsg("cookie无效");
        boolean ok = LoginCheck.checkCookie(cookieName, cookieValue);
        if (ok){
            result.setRespMsg("200");
            result.setRespMsg("cookie有效");
        }
        return result;
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }
}
