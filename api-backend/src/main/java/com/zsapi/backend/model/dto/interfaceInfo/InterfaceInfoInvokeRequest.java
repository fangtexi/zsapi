package com.zsapi.backend.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 测试接口请求
 * @TableName InterfaceInfo
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 请求参数
     */
    private String userRequestParams;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}