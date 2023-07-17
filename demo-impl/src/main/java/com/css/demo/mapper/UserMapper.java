package com.css.demo.mapper;

import com.css.demo.component.mybatis.model.User;
import com.css.demo.model.user.UpsertUserRequest;
import com.css.demo.model.user.UserVO;
import com.google.common.base.Strings;

import java.util.UUID;

public class UserMapper {

    public static UserVO toUserVO(User user) {
        return UserVO
                .builder()
                .id(user.getId().toString())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }

    public static User toUser(UpsertUserRequest request) {
        return User
                .builder()
                .id(Strings.isNullOrEmpty(request.getId()) ? UUID.randomUUID().toString() : request.getId())
                .name(request.getName())
                .phone(request.getPhone())
                .status(1)
                .build();
    }

}
