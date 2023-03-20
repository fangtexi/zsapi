package com.zsapi.client;

import com.zsapi.client.client.ZsApiClient;
import com.zsapi.client.modal.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiClientApplicationTests {

    @Test
    void contextLoads() {
        ZsApiClient zsApiClient = new ZsApiClient("2294ad69a3c0f703b3e0c66ce5fa53b9","ea5301967be0fe114c851f4b5dc8d130");
        String name = zsApiClient.getUserNameByPost(new User("zzs"));
        System.out.println(name);
    }

}
