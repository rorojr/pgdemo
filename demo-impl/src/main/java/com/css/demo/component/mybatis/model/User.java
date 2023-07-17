package com.css.demo.component.mybatis.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class User implements Serializable {
    private String id;

    private String name;

    private String phone;

    private Integer status;

}