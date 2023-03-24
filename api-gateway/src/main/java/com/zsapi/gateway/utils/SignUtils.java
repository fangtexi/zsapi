package com.zsapi.gateway.utils;


import cn.hutool.crypto.digest.DigestUtil;

/**
 * @Description 生成用户签名
 * @ClassName SignUtils
 * @Author 23951
 * @Date 2023/3/19 19:01
 * @Version 1.0
 */

public class SignUtils {

    /**
     * @description: 生成签名
     * @author: zzs
     * @date: 2023/3/19 19:01
     * @param: accessKey
     * @param: secretKey
     * @return: java.lang.String
     **/
    public static String getSign(String accessKey,String secretKey) {
        // accessKey 拼接 secretKey
        String content = accessKey + "." + secretKey;

        return DigestUtil.md5Hex(content);
    }

}
