package taozi.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import taozi.TTApi.TTRequest;
import taozi.util.RepositoryProvider;

import java.util.Collections;

@Configuration
public class BeanConfiguration {

    @Bean
    public UsernamePasswordCredentialsProvider getUsernamePasswordCredentialsProvider() {
        return new UsernamePasswordCredentialsProvider(DetailConfiguration.username, DetailConfiguration.password);
    }

    @Bean
    public CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(150).setConnectTimeout(500).setSocketTimeout(1000).build();
        return HttpClients.custom().setMaxConnTotal(1024).setMaxConnPerRoute(1024).setDefaultRequestConfig(config).build();
    }

    @Bean
    public TTRequest getTTRequest() {
        TTRequest ttRequest = new TTRequest();
        ttRequest.setMsg("taozi");
        ttRequest.setTo_user_list(Collections.singletonList("S9037218"));
        ttRequest.setBizAlarm("sjsj");
        return ttRequest;
    }

    @Bean
    public RepositoryProvider getRepositoryProvider() {
        return new RepositoryProvider(DetailConfiguration.repositoryDir);
    }

}
