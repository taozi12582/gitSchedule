package taozi.TTApi;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class TTPost {

    private static Logger log = LoggerFactory.getLogger(TTPost.class);

    public static Boolean doPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity s = new StringEntity(JSON.toJSONString(getTTRequest()), StandardCharsets.UTF_8);
            s.setContentType("application/json");
            s.setContentEncoding("utf-8");
            httpPost.setEntity(s);
            response = getHttpClient().execute(httpPost);
            if (null != response && response.getStatusLine().getStatusCode() == 200) {
                log.info("TTAPI请求成功");
                return true;
            }
        } catch (Exception e) {
            log.info("ttAPI请求失败");
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                log.info("response关闭失败");
            }
        }
        return false;
    }

    private static CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(150).setConnectTimeout(500).setSocketTimeout(1000).build();
        return HttpClients.custom().setMaxConnTotal(1024).setMaxConnPerRoute(1024).setDefaultRequestConfig(config).build();
    }

    private static TTRequest getTTRequest() {
        TTRequest ttRequest = new TTRequest();
        ttRequest.setMsg("taozi");
        ttRequest.setTo_user_list(Collections.singletonList("S9037218"));
        ttRequest.setBizAlarm("sjsj");
        return ttRequest;
    }

}
