package com.css.demo.dao;

import com.css.demo.component.mybatis.jdbc.UserMapper;
import com.css.demo.component.mybatis.model.User;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public Optional<User> queryUserById(String userId) {
        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(userMapper.getUserById(userId));
    }


    public List<User> queryUsers(String name, String phone) {
        if (Strings.isNullOrEmpty(name) && Strings.isNullOrEmpty(phone)){
            return Lists.newArrayList();
        }
        return userMapper.queryUsers(name, phone);
    }

    public void createUser(User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("User is null");
        }
        userMapper.insert(user);
    }

    public int updateUser(User user) {
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new RuntimeException("User is null");
        }
        return userMapper.update(user);
    }

    public void deleteUserById(String userId) {
        if (Objects.isNull(userId)) {
            throw new RuntimeException("Failed to deleteUserById");
        }
        userMapper.deleteById(userId);
    }
}
