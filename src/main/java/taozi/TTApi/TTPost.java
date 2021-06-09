package taozi.TTApi;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;


@Component
public class TTPost {

    private static Logger log = LoggerFactory.getLogger(TTPost.class);

    CloseableHttpClient closeableHttpClient;
    TTRequest ttRequest;

    public TTPost(CloseableHttpClient closeableHttpClient, TTRequest ttRequest) {
        this.closeableHttpClient = closeableHttpClient;
        this.ttRequest = ttRequest;
    }

    public Boolean doPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            StringEntity s = new StringEntity(JSON.toJSONString(ttRequest), StandardCharsets.UTF_8);
            s.setContentType("application/json");
            s.setContentEncoding("utf-8");
            httpPost.setEntity(s);
            response = closeableHttpClient.execute(httpPost);
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


}
