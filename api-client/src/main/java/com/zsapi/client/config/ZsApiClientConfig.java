package com.zsapi.client.config;

import com.zsapi.client.client.ZsApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @ClassName ZsApiClientConfig
 * @Author 23951
 * @Date 2023/3/19 19:02
 * @Version 1.0
 */
@Data
@Configuration
@ConfigurationProperties("zsapi.client")
@ComponentScan
public class ZsApiClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public ZsApiClient zsApiClient() {
        return  new ZsApiClient(accessKey,secretKey);
    }


}
