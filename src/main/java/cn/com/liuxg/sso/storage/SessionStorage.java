package cn.com.liuxg.sso.storage;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public enum SessionStorage {
    INSTANCE;
    private Map<String, HttpSession> sessionMap = new HashMap<>(1);
    public void set(String token,HttpSession session){
        sessionMap.put(token,session);
    }

    public HttpSession get(String token){
        return sessionMap.get(token);
    }

}
