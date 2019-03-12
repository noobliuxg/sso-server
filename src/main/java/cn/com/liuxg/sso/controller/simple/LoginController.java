package cn.com.liuxg.sso.controller.simple;

import cn.com.liuxg.sso.utils.LoginCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sso")
public class LoginController {

    /**
     *
     * @param userName
     * @param password
     * @param gotoUrl
     * @param response
     * @return
     */
    @PostMapping("/doLogin")
    public ModelAndView doLogin(String userName, String password,
                                String gotoUrl, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("login_fail");
        boolean ok = LoginCheck.checkLogin(userName, password);
        if (ok){
            Cookie cookie = new Cookie(LoginCheck.COOKIE_NAME,LoginCheck.COOKIE_VALUE);
            cookie.setPath("/");
            response.addCookie(cookie);
            modelAndView.setViewName("redirect:"+gotoUrl);
        }
        return modelAndView;
    }


    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }
}
