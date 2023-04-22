package com.zsapi.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zsapi.client.modal.entity.User;
import com.zsapi.common.service.inner.InnerUserService;
import com.zsapi.model.entity.Phone;
import com.zsapi.util.SignUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName
 * @Author 23951
 * @Date 2023/3/18 22:34
 * @Version 1.0
 */
@RestController
public class InterfaceInfoController {

    @DubboReference(check = false)
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
        return "你的名字是：" + user.getUserName();
    }

    /**
     * 获取手机号归属地
     * @param phone
     * @return
     */
    @GetMapping("/phonearea")
    public String getPhoneArea(com.zsapi.common.model.entity.Phone phone) {
        String phoneNumber = phone.getNumber();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("number",phoneNumber);
        String s = HttpUtil.get("https://cx.shouji.360.cn/phonearea.php", paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(s);

        Phone phoneObj = jsonObject.getJSONObject("data").toBean(Phone.class);
        String province = phoneObj.getProvince();
        String city = phoneObj.getCity();
        String sp = phoneObj.getSp();
        String res = new String(province + "-" + city + "-" + sp);

        return res;
    }

}
