package cn.com.liuxg.sso.controller.parent;

import cn.com.liuxg.sso.utils.HttpUtils;
import cn.com.liuxg.sso.utils.LoginCheck;
import cn.com.liuxg.sso.utils.RespMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DemoOneController {

    @RequestMapping("/demo1")
    public ModelAndView main(HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();

        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length>0){
            for (Cookie cookie : cookies){
                if (LoginCheck.COOKIE_NAME.equals(cookie.getName())){
                    //向校验服务器发送校验请求
                    String url = "http://check.liuxg.com:8080/sso/checkCookie";
                    RespMessage respMessage = HttpUtils.get(url, cookie.getName(), cookie.getValue());
                    if("200".equals(respMessage.getRespCode())){
                        modelAndView.setViewName("demo1");
                        return modelAndView;
                    }
                }
            }
        }
        modelAndView.addObject("gotoUrl", "http://demo1.liuxg.com:8080/demo1");
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
