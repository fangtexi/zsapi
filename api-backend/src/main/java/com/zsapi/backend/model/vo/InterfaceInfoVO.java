package com.zsapi.backend.model.vo;

import com.zsapi.common.model.entity.InterfaceInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 接口信息封装视图
 * @ClassName InterfaceInfoVO
 * @Author 23951
 * @Date 2023/4/6 22:31
 * @Version 1.0
 */
@Data
public class InterfaceInfoVO extends InterfaceInfo implements Serializable {
    /**
     * 调用次数
     */
    private Integer totalNum;

    private Integer leftNum;

    private static final Long serialVersionUID = 1L;
}
