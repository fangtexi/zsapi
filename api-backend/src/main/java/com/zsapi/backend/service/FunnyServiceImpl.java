package com.zsapi.backend.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsapi.backend.mapper.FunnyMapper;
import com.zsapi.common.model.entity.Funny;
import com.zsapi.common.service.FunnyService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 23951
* @description 针对表【funny】的数据库操作Service实现
* @createDate 2023-04-07 21:51:10
*/
@DubboService
public class FunnyServiceImpl extends ServiceImpl<FunnyMapper, Funny>
    implements FunnyService{

    @Resource
    private FunnyMapper funnyMapper;

    @Override
    public Funny getFunnyWordsRand() {

        Funny funnyWords = funnyMapper.getFunnyWordsRand();
        return funnyWords;
    }

}




