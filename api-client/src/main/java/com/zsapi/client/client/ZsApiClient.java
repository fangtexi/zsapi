package com.zsapi.client.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.zsapi.client.modal.entity.User;
import com.zsapi.client.util.SignUtils;
import com.zsapi.common.model.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 调用第三方接口的客户端
 * @ClassName ZsApiClient
 * @Author 23951
 * @Date 2023/3/19 19:05
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class ZsApiClient {

    private String accessKey;
    private String secretKey;

    private final String REQUEST_PREFIX = "/api";
    private final String HOST = "http://120.79.49.242:8125";
    private final String LOCAL_HOST = "http://localhost:8125";

    /**
     * @description: 封装请求头
     * @author: zzs
     * @date: 2023/3/19 19:07
     * @return: Map<String,String>
     **/
    public Map<String,String> getHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("accessKey",accessKey);
        map.put("sign", SignUtils.getSign(accessKey,secretKey));
        return map;
    }

    /**
     * @description: 获取姓名
     * @author: zzs
     * @date: 2023/3/19 19:09
     * @param: user
     * @return: java.lang.String
     **/
    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        return HttpRequest.post(HOST + REQUEST_PREFIX + "/name/user")
                // 添加请求头
                .addHeaders(getHeaders())
                .body(json)
                .execute().body();
    }

    /**
     * 获取搞笑语句
     * @author: zzs
     * @date: 2023/4/3 22:21
     * @return: java.lang.String
     **/
    public String getFunnyWordsRand() {

        return HttpRequest.get(HOST + REQUEST_PREFIX + "/funny/getFunnyWordsRand")
                // 添加请求头
                .addHeaders(getHeaders())
                .execute().body();
    }

    /**
     * 获取手机号归属地
     * @param phone
     * @return
     */
    public String getPhoneArea(Phone phone) {
        return HttpRequest.get(HOST + REQUEST_PREFIX + "/phonearea?number=" + phone.getNumber())
                // 添加请求头
                .addHeaders(getHeaders())
                .execute().body();
    }

}
