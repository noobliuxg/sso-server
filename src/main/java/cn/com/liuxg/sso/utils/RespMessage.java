package cn.com.liuxg.sso.utils;

import java.io.Serializable;
import java.util.Map;

public class RespMessage implements Serializable {

    private String respCode;
    private String respMsg;

    private Map<String,Object> respArgs;

    public RespMessage() {
    }

    public RespMessage(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Map<String, Object> getRespArgs() {
        return respArgs;
    }

    public void setRespArgs(Map<String, Object> respArgs) {
        this.respArgs = respArgs;
    }
}
