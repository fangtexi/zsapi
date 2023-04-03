package com.zsapi.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsapi.common.model.entity.InterfaceInfo;

/**
* @author 23951
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-18 21:45:14
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * @description: 参数校验
     * @author: zzs
     * @date: 2023/3/24 20:47 
     * @param: interfaceInfo
     * @param: b
     **/
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);


}
