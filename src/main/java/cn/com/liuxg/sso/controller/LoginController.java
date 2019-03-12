package cn.com.liuxg.sso.controller;

import cn.com.liuxg.sso.constant.AuthConst;
import cn.com.liuxg.sso.dao.UserDao;
import cn.com.liuxg.sso.entity.User;
import cn.com.liuxg.sso.storage.ClientStorage;
import cn.com.liuxg.sso.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, User input, Model model){

        User user = userDao.find(input);
        if (user==null){
            model.addAttribute("error","username or password is wrong");
            return  "redirect:/";
        }

        HttpSession session = request.getSession();
        String token = UUID.randomUUID().toString();
        session.setAttribute(AuthConst.TOKEN,token);
        session.setAttribute(AuthConst.IS_LOGIN,AuthConst.IS_LOGIN);

        String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
        if (!StringUtils.isEmpty(clientUrl)){
            return "redirect:"+clientUrl+"?token="+token;
        }

        return "redirect:/";
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();

        String token = (String)session.getAttribute(AuthConst.TOKEN);

        //销毁会话
        if (session!=null){
            session.invalidate();
        }

        List<String> urls = ClientStorage.INSTANCE.get(token);

        urls.forEach(url->{
            Map<String,String> map = new HashMap<>();
            map.put(AuthConst.LOGOUT_REQUEST,token);
            HttpUtils.post(url,map);
        });
        return "redirect:/";
    }

}
