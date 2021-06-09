package taozi.config;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class BeanConfiguration {

    @Bean
    public UsernamePasswordCredentialsProvider getUsernamePasswordCredentialsProvider(){
        return new UsernamePasswordCredentialsProvider(DetailConfiguration.username, DetailConfiguration.password);
    }



}
