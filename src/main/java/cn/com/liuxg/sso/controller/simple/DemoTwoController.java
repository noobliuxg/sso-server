package cn.com.liuxg.sso.controller.simple;

import cn.com.liuxg.sso.utils.LoginCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DemoTwoController {

    @RequestMapping("/demo2")
    public ModelAndView demo2(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("demo2");
        boolean ok = LoginCheck.checkCookie(request);
        if (!ok){
            modelAndView.setViewName("login");
            modelAndView.addObject("gotoUrl","/demo2");
        }
        return modelAndView;
    }
}
