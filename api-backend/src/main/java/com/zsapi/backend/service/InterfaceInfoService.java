package com.zsapi.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsapi.backend.model.entity.InterfaceInfo;

/**
* @author 23951
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-18 21:45:14
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);
}
