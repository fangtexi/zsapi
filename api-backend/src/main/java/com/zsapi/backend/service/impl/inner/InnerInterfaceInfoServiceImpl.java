package com.zsapi.backend.service.impl.inner;/**
 * @author zzs
 * @date 2023/3/24 21:47:24
 * @version 1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zsapi.backend.common.ErrorCode;
import com.zsapi.backend.exception.BusinessException;
import com.zsapi.backend.mapper.InterfaceInfoMapper;
import com.zsapi.common.model.entity.InterfaceInfo;
import com.zsapi.common.service.inner.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName InnerInterfaceInfoServiceImpl
 * @Author 23951
 * @Date 2023/3/24 21:47
 * @Version 1.0
 */

public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (!StringUtils.isAnyBlank(path,method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterfaceInfo::getUrl,path);
        wrapper.eq(InterfaceInfo::getMethod,method);

        return interfaceInfoMapper.selectOne(wrapper);
    }
}
