package com.zsapi.backend;

import com.zsapi.client.client.ZsApiClient;
import com.zsapi.client.modal.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiBackendApplicationTests {

    @Autowired
    private ZsApiClient zsApiClient;

    @Test
    void contextLoads() {
//        String name = zsApiClient.getUserNameByPost(new User("zzs"));
//        String phoneArea = zsApiClient.getPhoneArea("18850482356");
//        System.out.println(phoneArea);
    }

}
