package com.zsapi.client.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.zsapi.client.modal.entity.User;
import com.zsapi.client.util.SignUtils;
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
     * @description: 调用模拟接口
     * @author: zzs
     * @date: 2023/3/19 19:09
     * @param: user
     * @return: java.lang.String
     **/
    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post("http://localhost:8125/api/name/user")
                // 添加请求头
                .addHeaders(getHeaders())
                .body(json)
                .execute().body();
        return result;
    }

}
