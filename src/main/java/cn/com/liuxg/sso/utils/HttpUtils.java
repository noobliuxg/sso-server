package cn.com.liuxg.sso.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtils {



    public static String post(String url,Map<String,String> params){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(url);

        try {
            if (params!=null && !params.isEmpty()){
                List<NameValuePair> list = new ArrayList<>();
                Iterator<String> iterator = params.keySet().iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    list.add(new BasicNameValuePair(next,params.get(next)));
                }
                post.setEntity(new UrlEncodedFormEntity(list,""));
            }

            HttpResponse httpResponse = httpClient.execute(post);

            if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                HttpEntity entity = httpResponse.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                StringBuffer buffer = new StringBuffer();
                for (;;){
                    String line = reader.readLine();
                    if (line==null){
                        break;
                    }
                    buffer.append(line);
                }
                return buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpClient!=null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static RespMessage get(String url,String cookieName,String cookieValue){
        RespMessage respMessage = new RespMessage();

        HttpURLConnection connection = null;
        URL targetUrl = null;
        BufferedReader br = null;
        try {
            targetUrl = new URL(url+"?cookieName="+cookieName+"&cookieValue="+cookieValue);
            connection = (HttpURLConnection) targetUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String temp = null;
            while((temp=br.readLine())!=null){
                sb.append(temp);
            }
            JSONObject parseObject = JSONObject.parseObject(sb.toString());

            respMessage.setRespCode(parseObject.getString("respCode"));
            respMessage.setRespMsg(parseObject.getString("respMsg"));
        } catch (Exception e) {
            e.printStackTrace();
            respMessage.setRespCode("500");
            respMessage.setRespMsg("发送Get请求无效");
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            if (connection!=null){
                connection.disconnect();
            }
        }
        return respMessage;
    }

    public static RespMessage get(String url, Map<String,String> param){
        RespMessage respMessage = new RespMessage();
        HttpURLConnection httpURLConnection = null;
        URL targetUrl = null;
        BufferedReader br = null;
        try{
            // 拼装请求参数
            StringBuffer targetUrlStr = new StringBuffer(url).append("?");
            for(Map.Entry<String, String> entry : param.entrySet()){
                targetUrlStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url = targetUrlStr.substring(0,targetUrlStr.length()-1);

            targetUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) targetUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            StringBuffer sb = new StringBuffer();
            String temp = null;
            while((temp=br.readLine())!=null){
                sb.append(temp);
            }

            JSONObject resultJson = JSONObject.parseObject(sb.toString());
            respMessage.setRespCode(resultJson.getString("respCode"));
            respMessage.setRespMsg(resultJson.getString("respMsg"));

            JSONObject resultJsonMap = JSONObject.parseObject(resultJson.getString("respArgs"));
            respMessage.setRespArgs(resultJsonMap);
            return respMessage;
        }catch (Exception e) {
            respMessage.setRespCode("500");
            respMessage.setRespMsg("请求发起失败");
            return respMessage;
        }finally {
            if (br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            if(httpURLConnection !=null){
                httpURLConnection.disconnect();
            }
        }
    }
}
