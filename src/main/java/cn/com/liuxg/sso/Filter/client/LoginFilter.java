package cn.com.liuxg.sso.Filter.client;


import cn.com.liuxg.sso.constant.AuthConst;
import cn.com.liuxg.sso.storage.SessionStorage;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    private FilterConfig config = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        //已登入
        HttpSession session = request.getSession();
        if (session.getAttribute(AuthConst.IS_LOGIN)!=null){
            filterChain.doFilter(request,response);
            return ;
        }

        //去认证中心获取到了token
        String token = request.getParameter(AuthConst.TOKEN);
        if (!StringUtils.isEmpty(token)){
            session.setAttribute(AuthConst.IS_LOGIN,AuthConst.IS_LOGIN);
            SessionStorage.INSTANCE.set(token,session);
            filterChain.doFilter(request,response);
            return ;
        }

        //未登录
        response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL)+"?clientUrl="+request.getRequestURL());
    }

    @Override
    public void destroy() {
    }
}
