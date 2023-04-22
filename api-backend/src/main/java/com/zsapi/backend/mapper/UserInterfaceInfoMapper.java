package com.zsapi.backend.mapper;

import com.zsapi.common.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 23951
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2023-04-06 22:37:50
* @Entity com.zsapi.common.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    /**
     * 被调用最多的接口 TOP limit
     * @author: zzs
     * @date: 2023/4/6 22:48
     * @param: limit
     * @return: java.util.List<com.zsapi.common.model.entity.UserInterfaceInfo>
     **/
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}




