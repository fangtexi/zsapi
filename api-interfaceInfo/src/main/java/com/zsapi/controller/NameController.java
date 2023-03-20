package com.zsapi.controller;

import com.zsapi.model.entity.User;
import com.zsapi.util.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @ClassName NameController
 * @Author 23951
 * @Date 2023/3/18 22:34
 * @Version 1.0
 */
@RestController
@RequestMapping("name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(String name) {
        return "Get 你的名字是：" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "Post 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        // 获取请求头中的sign
        String sign = request.getHeader("sign");
        // todo 实际上服务端该从数据库获取用户的accessKey和 secretKey通过相同的加密算法生成签名进行验证
        String sign1 = SignUtils.getSign("2294ad69a3c0f703b3e0c66ce5fa53b9", "ea5301967be0fe114c851f4b5dc8d130");
        if (!sign1.equals(sign)) {
            throw new RuntimeException("无权限！");
        }
        return "Post 你的名字是：" + user.getUserName();
    }
}
