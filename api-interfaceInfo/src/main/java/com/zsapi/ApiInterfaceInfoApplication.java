package com.zsapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApiInterfaceInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiInterfaceInfoApplication.class, args);
        log.info("***** 接口系统启动成功 *****");
    }
}
