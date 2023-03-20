package com.zsapi.backend.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 接口状态枚举
 * @ClassName InterfaceInfoStatusEnum
 * @Author 23951
 * @Date 2023/3/19 20:02
 * @Version 1.0
 */
public enum InterfaceInfoStatusEnum {
    OFFLINE("关闭",0),
    ONLINE("上线",1);

    private final String text;
    private final int value;

    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
