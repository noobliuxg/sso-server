package cn.com.liuxg.sso.entity;

public class Auth {
    /**
     * login user
     */
    private User user;
    /**
     * random
     */
    private String code;
    /**
     * expire date
     */
    private long expire;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
