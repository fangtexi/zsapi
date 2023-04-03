package com.zsapi;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiInterfaceInfoApplicationTests {

    @Test
    void contextLoads() {
        HttpResponse execute = HttpRequest.get("https://v.api.aa1.cn/api/tiangou/")
                .execute();
        System.out.println(execute.body());
    }

}
