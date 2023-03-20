package com.zsapi.backend.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName InterfaceInfo
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private String requestHeader;

    /**
     *
     */
    private String responseHeader;

    /**
     *
     */
    private String method;

    /**
     * 创建人
     */
    private Long userId;


    /**
     * 请求参数
     */
    private String requestParams;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}