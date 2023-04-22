package com.zsapi.backend.mapper;

import com.zsapi.common.model.entity.Funny;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 23951
* @description 针对表【funny】的数据库操作Mapper
* @createDate 2023-04-07 21:51:10
* @Entity com.zsapi.common.model.entity.Funny
*/
public interface FunnyMapper extends BaseMapper<Funny> {

    /**
     * 随机获取一条数据
     * @author: zzs
     * @date: 2023/4/7 21:41
     * @return: com.zsapi.common.model.entity.Funny
     **/
    Funny getFunnyWordsRand();

}




