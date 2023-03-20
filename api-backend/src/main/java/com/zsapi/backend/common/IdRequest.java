package com.zsapi.backend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @ClassName IdRequest
 * @Author 23951
 * @Date 2023/3/19 19:47
 * @Version 1.0
 */

@Data
public class IdRequest implements Serializable {

    private Long id;

    private static final long serialVersionUID = -578805505161971397L;
}
