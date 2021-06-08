

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import taozi.TTApi.TTRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;


public class ApiTest {


    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity;
            entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }


    public static void main(String[] args) {
//        doGet("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071", "UTF-8");
//        System.out.println("-----------分割线------------");
//        System.out.println("-----------分割线------------");
//        System.out.println("-----------分割线------------");
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("msg", "taozi");
//        jsonObject.put("to_user_list", Collections.singletonList("S9037218"));

        System.out.println(JSON.toJSONString(getTTRequest()));
        String s = doPostJson("http://msg.ads.oppo.local/api/tt_push", JSON.toJSONString(getTTRequest()));
        System.out.println(s);
    }

    private static TTRequest getTTRequest() {
        TTRequest ttRequest = new TTRequest();
        ttRequest.setMsg("taozi");
        ttRequest.setTo_user_list(Collections.singletonList("S9037218"));
        ttRequest.setBizAlarm("sjsj");
        return ttRequest;
    }


}
