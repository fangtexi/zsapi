package com.zsapi.backend.service.inner;

/**
 * @author zzs
 * @date 2023/3/25 20:48:01
 * @version 1.0
 */

import com.zsapi.common.service.UserInterfaceInfoService;
import com.zsapi.common.service.inner.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description TODO
 * @ClassName InnerUserInterfaceInfoServiceImpl
 * @Author 23951
 * @Date 2023/3/25 20:48
 * @Version 1.0
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Autowired
    private UserInterfaceInfoService userInterfaceInfoService;
    // 增加invokeCount方法的并发安全性

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
