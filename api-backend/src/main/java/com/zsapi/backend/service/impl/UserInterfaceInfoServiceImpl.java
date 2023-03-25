package com.zsapi.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsapi.backend.mapper.UserInterfaceInfoMapper;
import com.zsapi.common.model.entity.UserInterfaceInfo;
import com.zsapi.common.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author 23951
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-03-25 20:37:54
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

}




