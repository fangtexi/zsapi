package com.zsapi.common.service.inner;

/**
 * @Description 用户接口信息表
 * @ClassName InnerUserInterfaceInfoService
 * @Author 23951
 * @Date 2023/3/25 20:46
 */
public interface InnerUserInterfaceInfoService {

    /*
     *  接口调用次数统计 (user)
     * @author: zzs
     * @date: 2023/3/25 20:47
     * @param: interfaceInfoId 接口id
     * @param: userId 用户id
     * @return: boolean
     **/
    boolean invokeCount(long interfaceInfoId,long userId);

    /**
     * 增加接口调用次数 (admin)
     * @author: zzs
     * @date: 2023/4/4 21:52
     * @param: interfaceInfoId
     * @param: userId
     * @return: boolean
     **/
    boolean invokeCountAdmin(long interfaceInfoId,long userId);

    /**
     * 获取剩余调用次数
     * @author: zzs
     * @date: 2023/4/4 21:16
     * @param: interfaceInfoId
     * @param: userId
     * @return: boolean
     **/
    boolean getLeftNum(long interfaceInfoId, long userId);
}
