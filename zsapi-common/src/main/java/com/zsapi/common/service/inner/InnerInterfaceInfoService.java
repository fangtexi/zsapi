package com.zsapi.common.service.inner;
import com.zsapi.common.model.entity.InterfaceInfo;

/**
 * @ClassName InnerInterfaceInfoService
 * @Author 23951
 * @Date 2023/3/24 21:44
 */
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法）
     * @author: zzs
     * @date: 2023/3/24 21:45
     * @param: path 请求路径
     * @param: method 请求方法
     * @return: com.zsapi.common.model.entity.InterfaceInfo
     **/
    InterfaceInfo getInterfaceInfo(String path,String method);
}
