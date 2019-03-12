package cn.com.liuxg.sso.Filter;

import cn.com.liuxg.sso.constant.AuthConst;
import cn.com.liuxg.sso.storage.ClientStorage;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CertificateFilter implements Filter {
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

        String uri = request.getRequestURI();

        HttpSession session = request.getSession();
        //已经登录
        if (session.getAttribute(AuthConst.IS_LOGIN)!=null){
            String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
            String token = (String) session.getAttribute(AuthConst.TOKEN);
            if (!StringUtils.isEmpty(clientUrl)){
                ClientStorage.INSTANCE.set(token,clientUrl);
                response.sendRedirect(clientUrl+"?token="+token);
                return ;
            }
            if (!"/success".equals(uri)){
                response.sendRedirect("/success");
                return ;
            }
            filterChain.doFilter(servletRequest,servletResponse);
            return ;
        }

        //登录请求,则放行
        if ("/".equals(uri) || "/login".equals(uri) || "/logout".equals(uri)){
            filterChain.doFilter(request,response);
            return ;
        }

        //未登录，则拦截请求
        response.sendRedirect("/");
    }

    @Override
    public void destroy() {
        config = null;
    }
}
