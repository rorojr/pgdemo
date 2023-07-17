package com.css.demo.controller;


import com.css.demo.component.mybatis.model.User;
import com.css.demo.mapper.UserMapper;
import com.css.demo.model.common.BaseMessage;
import com.css.demo.model.common.IResult;
import com.css.demo.model.common.ResultBuilder;
import com.css.demo.model.user.UpsertUserRequest;
import com.css.demo.model.user.UserVO;
import com.css.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("{userId}")
    public IResult<UserVO> getUser(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("")
    public IResult<UserVO> createUser(@RequestBody UpsertUserRequest upsertUserRequest) {
        Optional<User> user = userService.createUser(upsertUserRequest);
        if (user.isEmpty()){
            return ResultBuilder.build(BaseMessage.FAIL);
        }
        return ResultBuilder.build(BaseMessage.SUCCESS, UserMapper.toUserVO(user.get()));
    }

    @PutMapping("{userId}")
    public IResult<UserVO> updateUser(
        @PathVariable("userId") String userId,
        @RequestBody UpsertUserRequest upsertUserRequest,
        HttpServletRequest request) {
        Optional<User> user = userService.updateUser(upsertUserRequest);
        if (user.isEmpty()){
            return ResultBuilder.build(BaseMessage.FAIL);
        }
        return ResultBuilder.build(BaseMessage.SUCCESS, UserMapper.toUserVO(user.get()));
    }

    @DeleteMapping("{userId}")
    public IResult<Void> deleteUser(@PathVariable("userId") String userId, HttpServletRequest request) {
        userService.deleteUserById(userId);
        return ResultBuilder.build(BaseMessage.SUCCESS);
    }

}
