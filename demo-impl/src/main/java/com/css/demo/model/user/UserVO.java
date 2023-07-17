package com.css.demo.model.user;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserVO implements Serializable {
    private String id;
    private String name;
    private String phone;
}
