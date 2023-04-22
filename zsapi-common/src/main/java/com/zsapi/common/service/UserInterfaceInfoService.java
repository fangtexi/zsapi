package com.zsapi.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsapi.common.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 23951
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-04-04 20:30:43
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 增加接口调用次数
     * @author: zzs
     * @date: 2023/4/4 20:59
     * @param: interfaceInfoId
     * @param: userId
     * @return: boolean
     **/
    boolean invokeCount(long interfaceInfoId,long userId);

    /**
     * 接口调用次数统计 (admin)
     * @author: zzs
     * @date: 2023/4/4 21:53
     * @param: interfaceInfoId
     * @param: userId
     * @return: boolean
     **/
    boolean invokeCountAdmin(long interfaceInfoId,long userId);

    /**
     * 获取剩余调用次数
     * @author: zzs
     * @date: 2023/4/4 21:13
     * @param: interfaceInfoId
     * @param: userId
     * @return: boolean
     **/
    boolean getLeftNum(long interfaceInfoId,long userId);

    /**
     * 被调用最多的接口 TOP limit (admin) 用于统计接口调用次数最多的接口
     * @author: zzs
     * @date: 2023/4/6 22:51
     * @param: limit
     * @return: java.util.List<com.zsapi.common.model.entity.UserInterfaceInfo>
     **/
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);




}
