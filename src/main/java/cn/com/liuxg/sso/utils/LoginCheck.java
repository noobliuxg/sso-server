package cn.com.liuxg.sso.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class LoginCheck {

    private static final String USERNAME = "user";

    private static final String PASSWORD = "123456";

    public static final String COOKIE_NAME = "ssocookie";

    public static final String COOKIE_VALUE = "sso";

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    public static boolean checkLogin(String userName,String password){
        if (USERNAME.equalsIgnoreCase(userName)&&PASSWORD.equalsIgnoreCase(password)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     *
     * @param request
     * @return
     */
    public static boolean checkCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (COOKIE_NAME.equalsIgnoreCase(cookie.getName()) && COOKIE_VALUE.equalsIgnoreCase(cookie.getValue())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static boolean checkCookie(String cookieName,String cookieValue){
        if (COOKIE_NAME.equalsIgnoreCase(cookieName) && COOKIE_VALUE.equalsIgnoreCase(cookieValue)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
