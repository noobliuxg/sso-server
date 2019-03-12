package cn.com.liuxg.sso.controller.complex;

import cn.com.liuxg.sso.utils.HttpUtils;
import cn.com.liuxg.sso.utils.LoginCheck;
import cn.com.liuxg.sso.utils.RespMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/b")
public class DemoTwoController {

    @RequestMapping("/demo2")
    public ModelAndView main(HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (LoginCheck.COOKIE_NAME.equals(cookie.getName())){
                String url = "http://check.x.com:8080/sso/checkCookie";
                RespMessage respMessage = HttpUtils.get(url, cookie.getName(), cookie.getValue());
                if ("200".equals(respMessage.getRespCode())){
                    modelAndView.setViewName("demo2");
                    return modelAndView;
                }
            }
        }
        modelAndView.addObject("gotoUrl", "http://demo1.x.com:8080/demo2");
        modelAndView.setViewName("login");
        return null;
    }
}
