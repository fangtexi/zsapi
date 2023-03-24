package com.zsapi.common.service.inner;
import com.zsapi.common.model.entity.User;

/**
 * @author: zzs
 * @date: 2023/3/24 21:46
 **/
public interface InnerUserService {

    /*
     * 数据库中查是否已分配给用户秘钥（accessKey）
     * @author: zzs
     * @date: 2023/3/24 21:32
     * @param: accessKey
     * @return: com.zsapi.common.model.entity.User
     **/
    User getInvokeUser(String accessKey);
}
