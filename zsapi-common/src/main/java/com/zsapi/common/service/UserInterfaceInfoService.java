package com.zsapi.common.service;

import com.zsapi.common.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 23951
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-03-25 20:37:54
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /*
     * 接口调用次数统计
     * @author: zzs
     * @date: 2023/3/25 20:49
     * @param: interfaceInfoId 接口id
     * @param: userId 用户id
     * @return: boolean
     **/
    boolean invokeCount(long interfaceInfoId, long userId);

}
