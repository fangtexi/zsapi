package com.zsapi.common.service.inner;/**
 * @author zzs
 * @date 2023/3/25 20:46:34
 * @version 1.0
 */

/**
 * @Description 用户接口信息表
 * @ClassName InnerUserInterfaceInfoService
 * @Author 23951
 * @Date 2023/3/25 20:46
 */
public interface InnerUserInterfaceInfoService {

    /*
     *  接口调用次数统计
     * @author: zzs
     * @date: 2023/3/25 20:47
     * @param: interfaceInfoId 接口id
     * @param: userId 用户id
     * @return: boolean
     **/
    boolean invokeCount(long interfaceInfoId,long userId);
}
