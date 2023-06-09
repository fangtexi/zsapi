package com.zsapi.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsapi.backend.mapper.InterfaceInfoMapper;
import com.zsapi.common.model.entity.InterfaceInfo;
import com.zsapi.common.service.InterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
* @author 23951
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-03-18 21:45:14
*/
@DubboService
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b) {

    }
}




