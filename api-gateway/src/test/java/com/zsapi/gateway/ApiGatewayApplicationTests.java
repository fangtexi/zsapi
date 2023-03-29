package com.zsapi.gateway;

import com.zsapi.common.model.entity.InterfaceInfo;
import com.zsapi.common.service.inner.InnerInterfaceInfoService;
import com.zsapi.common.service.inner.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiGatewayApplicationTests {

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @Test
    void contextLoads() {
        InterfaceInfo interfaceInfo = innerInterfaceInfoService.getInterfaceInfo("http://localhost:8123/name/user", "POST");
        System.out.println(interfaceInfo);
    }

}
