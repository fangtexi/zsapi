package com.zsapi.backend.service.inner;/**
 * @author zzs
 * @date 2023/3/24 21:39:21
 * @version 1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsapi.backend.common.ErrorCode;
import com.zsapi.backend.exception.BusinessException;
import com.zsapi.backend.mapper.UserMapper;
import com.zsapi.common.model.entity.User;
import com.zsapi.common.service.inner.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName InnerUserServiceImpl
 * @Author 23951
 * @Date 2023/3/24 21:39
 * @Version 1.0
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (!StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccessKey,accessKey);
        return userMapper.selectOne(wrapper);
    }
}
