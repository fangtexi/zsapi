package com.zsapi.controller;/**
 * @author zzs
 * @date 2023/4/7 21:43:22
 * @version 1.0
 */

import com.zsapi.common.model.entity.Funny;
import com.zsapi.common.service.FunnyService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @ClassName funnyWordsController
 * @Author 23951
 * @Date 2023/4/7 21:43
 * @Version 1.0
 */
@RestController
@RequestMapping("/funny")
public class funnyWordsController {


    @DubboReference(check = false)
    private FunnyService funnyService;

    /**
     * 随机获取一条数据
     * @author: zzs
     * @date: 2023/4/7 21:45
     * @return: com.zsapi.common.model.entity.Funny
     **/
    @GetMapping("/getFunnyWordsRand")
    public String getFunnyWordsRand() {
        Funny funnyWords = funnyService.getFunnyWordsRand();

        return funnyWords.getFunnyWords();
    }
}
