

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import taozi.TTApi.TTRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;


public class ApiTest {


    public static String doPost(String url, JSONObject json){
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        postMethod.addRequestHeader("accept", "*/*");
        postMethod.addRequestHeader("connection", "Keep-Alive");
        //设置json格式传送
        postMethod.addRequestHeader("Content-Type", "application/json;charset=utf-8");
        //必须设置下面这个Header
        postMethod.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
        //添加请求参数
        postMethod.addParameter("msg", json.getString("msg"));
        postMethod.addParameter("to_user_list",json.getString("to_user_list"));

        String res = "";
        try {
            int code = httpClient.executeMethod(postMethod);
            System.out.println(code);
            if (code == 200){
                System.out.println("200");
                res = postMethod.getResponseBodyAsString();
                System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
//        doGet("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13026194071", "UTF-8");
        System.out.println("-----------分割线------------");
        System.out.println("-----------分割线------------");
        System.out.println("-----------分割线------------");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "taozi");
        jsonObject.put("to_user_list",Collections.singletonList("S9037218"));
        doPost("http://commerce-ads-test.wanyol.com/sms/api/tt_push", jsonObject);
    }

    private static TTRequest getTTRequest(){
        TTRequest ttRequest = new TTRequest();
        ttRequest.setMsg("taozi");
        ttRequest.setTo_user_list(Collections.singletonList("S9037218"));
        ttRequest.setBizAlarm("sjsj");
        return ttRequest;
    }


}
