package com.zsapi.controller;

import com.zsapi.client.modal.entity.User;
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

    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "Get 你的名字是：" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "Post 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        // 获取请求头中的accesskey
        String accessKey = request.getHeader("accessKey");
        // 获取请求头中的sign
        String sign = request.getHeader("sign");
        // todo 实际上服务端该从数据库获取用户的accessKey和secretKey通过相同的加密算法生成签名进行验证（根据accessKey查找）
        String sign1 = SignUtils.getSign("21c35ad418a380484550512bc0306992", "3223d3e89b8bec50b2bab1095fb5cd9b");
        if (!sign1.equals(sign)) {
            throw new RuntimeException("无权限！");
        }
        return "Post 你的名字是：" + user.getUsername();
    }
}
