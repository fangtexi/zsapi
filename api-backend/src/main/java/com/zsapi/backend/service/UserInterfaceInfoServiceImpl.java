package com.zsapi.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsapi.backend.common.ErrorCode;
import com.zsapi.backend.exception.BusinessException;
import com.zsapi.backend.mapper.UserInterfaceInfoMapper;
import com.zsapi.common.constant.InterFaceInfoConstant;
import com.zsapi.common.model.entity.UserInterfaceInfo;
import com.zsapi.common.service.UserInterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 23951
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-04-04 20:30:43
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Autowired
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if (interfaceInfoId == 0 || userId == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 老用户调用接口
        LambdaUpdateWrapper<UserInterfaceInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        wrapper.eq(UserInterfaceInfo::getUserId, userId);
        // 剩余次数大于1
        wrapper.gt(UserInterfaceInfo::getLeftNum, 0);
        wrapper.setSql("leftnum = leftnum - 1,totalnum = totalnum + 1");
        return this.update(wrapper);
    }

    @Override
    public boolean invokeCountAdmin(long interfaceInfoId, long userId) {
        if (interfaceInfoId == 0 || userId == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断管理员是不是第一次调用接口
        synchronized ((Long)userId) {
            LambdaQueryWrapper<UserInterfaceInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
            wrapper.eq(UserInterfaceInfo::getUserId, userId);
            UserInterfaceInfo userInterfaceInfo = this.getOne(wrapper);
            if (userInterfaceInfo == null) {
                // 第一次调用
                UserInterfaceInfo userInterfaceInfo1 = new UserInterfaceInfo();
                userInterfaceInfo1.setInterfaceInfoId(interfaceInfoId);
                userInterfaceInfo1.setUserId(userId);
                userInterfaceInfo1.setLeftNum(InterFaceInfoConstant.INTERFACE_INFO_INVOKE_COUNT_ADMIN);
                userInterfaceInfo1.setTotalNum(1);
                return this.save(userInterfaceInfo1);
            }
            LambdaUpdateWrapper<UserInterfaceInfo> wrapper1 = new LambdaUpdateWrapper<>();
            wrapper1.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
            wrapper1.eq(UserInterfaceInfo::getUserId, userId);
            wrapper1.setSql("totalnum = totalnum + 1");
            return this.update(wrapper1);
        }
    }

    @Override
    public boolean getLeftNum(long interfaceInfoId, long userId) {
        if (interfaceInfoId == 0 || userId == 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否是第一次调用
        UserInterfaceInfo userInterfaceInfo = null;
        synchronized ((Long)userId) {
            LambdaQueryWrapper<UserInterfaceInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
            wrapper.eq(UserInterfaceInfo::getUserId, userId);
            userInterfaceInfo = this.getOne(wrapper);
            if (userInterfaceInfo == null) {
                // 第一次调用
                userInterfaceInfo = new UserInterfaceInfo();
                userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
                userInterfaceInfo.setUserId(userId);
                userInterfaceInfo.setLeftNum(InterFaceInfoConstant.INTERFACE_INFO_INVOKE_COUNT);
                return this.save(userInterfaceInfo);
            }
        }
        // 老用户调用接口
        Integer leftnum = userInterfaceInfo.getLeftNum();
        return leftnum > 0;
    }

    @Override
    public List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit) {
        return userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
    }
}




