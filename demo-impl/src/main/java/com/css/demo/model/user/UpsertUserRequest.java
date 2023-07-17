package com.css.demo.model.user;

import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;

@Data
public class UpsertUserRequest implements Serializable {

    @Nullable
    private String id;

    private String name;
    private String phone;
}
