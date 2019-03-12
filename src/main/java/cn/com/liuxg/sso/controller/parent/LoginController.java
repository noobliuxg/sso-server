package cn.com.liuxg.sso.controller.parent;

import cn.com.liuxg.sso.utils.LoginCheck;
import cn.com.liuxg.sso.utils.RespMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sso")
public class LoginController {

    @PostMapping("/doLogin")
    public ModelAndView doLogin(String userName, String password, String gotoUrl,
                                HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("login_fail");

        boolean ok = LoginCheck.checkLogin(userName, password);

        if (ok){
            modelAndView.setViewName("redirect:"+gotoUrl);
            Cookie cookie = new Cookie(LoginCheck.COOKIE_NAME,LoginCheck.COOKIE_VALUE);
            cookie.setDomain("liuxg.com");
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return modelAndView;
    }

    @GetMapping("/checkCookie")
    @ResponseBody
    public RespMessage checkCookie(String cookieName, String cookieValue){
        RespMessage respMessage = new RespMessage();
        respMessage.setRespCode("500");
        respMessage.setRespMsg("CookieName或CookieValue无效");
        boolean ok = LoginCheck.checkCookie(cookieName, cookieValue);
        if (ok){
            respMessage.setRespCode("200");
            respMessage.setRespMsg("登入成功！");
        }
        return respMessage;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

}
