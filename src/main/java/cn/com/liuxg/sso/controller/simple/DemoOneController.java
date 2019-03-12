package cn.com.liuxg.sso.controller.simple;

import cn.com.liuxg.sso.utils.LoginCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DemoOneController {

    @RequestMapping("/dome1")
    public ModelAndView dome1(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        boolean ok = LoginCheck.checkCookie(request);
        modelAndView.setViewName("demo1");
        if (!ok){
            modelAndView.setViewName("login");
            modelAndView.addObject("gotoUrl","/demo1");
        }
        return modelAndView;
    }
}
