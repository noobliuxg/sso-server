package cn.com.liuxg.sso.Filter.client;

import cn.com.liuxg.sso.constant.AuthConst;
import cn.com.liuxg.sso.storage.SessionStorage;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutFilter implements Filter {
    private FilterConfig config = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

       if ("/logout".equals(request.getRequestURI())){
           if (session!=null){
               session.invalidate();
           }
           response.sendRedirect(config.getInitParameter(AuthConst.LOGOUT_URL));
           return ;
       }

       String token = request.getParameter(AuthConst.LOGOUT_REQUEST);
       if (!StringUtils.isEmpty(token)){
           HttpSession httpSession = SessionStorage.INSTANCE.get(token);
           if (httpSession!=null){
                httpSession.invalidate();
           }
           return ;
       }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
