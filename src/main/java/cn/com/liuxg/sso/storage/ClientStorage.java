package cn.com.liuxg.sso.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ClientStorage {
    INSTANCE;
    private Map<String, List<String>> tokenMap = new HashMap<>(10);

    public void set(String token,String url){
        if (!tokenMap.containsKey(token)){
            List<String> urls = new ArrayList<>();
            urls.add(url);
            tokenMap.put(token,urls);
        }
        tokenMap.get(token).add(url);
    }

    public List<String> get(String token){
        return tokenMap.get(token);
    }
}
