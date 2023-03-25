package com.zsapi.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsapi.backend.common.ErrorCode;
import com.zsapi.backend.exception.BusinessException;
import com.zsapi.backend.mapper.UserInterfaceInfoMapper;
import com.zsapi.common.model.entity.UserInterfaceInfo;
import com.zsapi.common.service.UserInterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 23951
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-03-25 20:37:54
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {

        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaUpdateWrapper<UserInterfaceInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInterfaceInfo::getInterfaceinfoid,interfaceInfoId);
        wrapper.eq(UserInterfaceInfo::getUserid,userId);
        wrapper.setSql("leftNum = leftNum - 1,totalNum = totalNum + 1");

        return this.update(wrapper);
    }
}




