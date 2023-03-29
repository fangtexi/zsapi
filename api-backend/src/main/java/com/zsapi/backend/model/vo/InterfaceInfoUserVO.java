package com.zsapi.backend.model.vo;/**
 * @author zzs
 * @date 2023/3/29 20:57:32
 * @version 1.0
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description TODO
 * @ClassName InterfaceInfoUserVO
 * @Author 23951
 * @Date 2023/3/29 20:57
 * @Version 1.0
 */
@Data
public class InterfaceInfoUserVO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     *
     */
    private String name;

    /**
     * 接口状态
     */
    private int status;

    /**
     *
     */
    private String description;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 请求uri
     */
    private String path;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
