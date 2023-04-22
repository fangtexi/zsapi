package com.zsapi.common.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;

    private String number;
    private String province;
    private String city;
    private String sp;
}
