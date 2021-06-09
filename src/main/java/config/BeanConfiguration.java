package config;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UsernamePasswordCredentialsProvider getUsernamePasswordCredentialsProvider(){
        return new UsernamePasswordCredentialsProvider(DetailConfiguration.username, DetailConfiguration.password);
    }



}
