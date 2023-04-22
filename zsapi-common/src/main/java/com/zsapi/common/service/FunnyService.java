package com.zsapi.common.service;

import com.zsapi.common.model.entity.Funny;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 23951
* @description 针对表【funny】的数据库操作Service
* @createDate 2023-04-07 21:51:10
*/
public interface FunnyService extends IService<Funny> {

    /**
     * 随机获取一条数据
     * @author: zzs
     * @date: 2023/4/7 21:42
     * @return: com.zsapi.common.model.entity.Funny
     **/
    Funny getFunnyWordsRand();

}
