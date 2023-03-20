package com.zsapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ApiInterfaceInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiInterfaceInfoApplication.class, args);
        log.info("***** 接口系统启动成功 *****");
    }

}
