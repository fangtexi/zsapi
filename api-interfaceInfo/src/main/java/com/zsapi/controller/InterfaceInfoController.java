package com.zsapi.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zsapi.client.modal.entity.User;
import com.zsapi.common.service.inner.InnerUserService;
import com.zsapi.util.SignUtils;
import org.apache.dubbo.config.annotation.DubboReference;
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
public class InterfaceInfoController {

    @DubboReference
    private InnerUserService innerUserService;

    /**
     * 用户鉴权
     * @author: zzs
     * @date: 2023/4/3 20:20
     * @param: request
     * @return: boolean
     **/
    public void handleAuth(HttpServletRequest request) {
        // 获取请求头中的 accesskey
        String accessKey = request.getHeader("accessKey");
        // 获取请求头中的 sign
        String sign = request.getHeader("sign");
        // 根据 accessKey 获取 secretKey
        com.zsapi.common.model.entity.User user = innerUserService.getInvokeUser(accessKey);
        String secretKey = user.getSecretKey();
        // 通过相同的加密算法生成签名进行验证
        String sign1 = SignUtils.getSign(accessKey, secretKey);
        if (!sign1.equals(sign)) {
            throw new RuntimeException("无权限！");
        }
    }

    /**
     * 获取姓名
     * @author: zzs
     * @date: 2023/4/3 20:45
     * @param: user
     * @param: request
     * @return: java.lang.String
     **/
    @PostMapping("/name/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        handleAuth(request);
        return "Post 你的名字是：" + user.getUserName();
    }

    /**
     * 获取舔狗语录
     * @author: zzs
     * @date: 2023/4/3 20:47
     * @param: request
     * @return: java.lang.String
     **/
    @GetMapping("/love/words")
    public String getLoveWords(HttpServletRequest request) {
        handleAuth(request);
        HttpResponse execute = HttpRequest.get("https://v.api.aa1.cn/api/tiangou/")
                .execute();
        return execute.body();
    }

    /**
     * 获取搞笑语录
     * @author: zzs
     * @date: 2023/4/3 22:24
     * @param: request
     * @return: java.lang.String
     **/
    @GetMapping("/funny/words")
    public String getFunnyWords(HttpServletRequest request) {
        handleAuth(request);
        HttpResponse execute = HttpRequest.get("https://v.api.aa1.cn/api/api-wenan-gaoxiao/index.php?aa1=json")
                .execute();
        System.out.println(execute.body());
        JSONArray jsonArray = JSONUtil.parseArray(execute.body());
        JSONObject object = jsonArray.getJSONObject(0);
        String gaoxiao = (String) object.get("gaoxiao");

        return gaoxiao;
    }

    /**
     * 获取ip地址信息
     * @author: zzs
     * @date: 2023/4/3 22:37
     * @param: request
     * @return: java.lang.String
     **/
    @GetMapping("/ip")
    public String getIp(HttpServletRequest request) {
        handleAuth(request);
        HttpResponse execute = HttpRequest.get("https://v.api.aa1.cn/api/myip/index.php?aa1=json")
                .execute();
        JSONObject entries = JSONUtil.parseObj(execute.body());
        String ip = (String) entries.get("myip");
        return ip;
    }




}
