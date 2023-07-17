package com.css.demo.service;

import com.css.demo.component.error.BizException;
import com.css.demo.component.mybatis.model.User;
import com.css.demo.dao.UserDao;
import com.css.demo.mapper.UserMapper;
import com.css.demo.model.common.BaseMessage;
import com.css.demo.model.common.IResult;
import com.css.demo.model.common.ResultBuilder;
import com.css.demo.model.user.UpsertUserRequest;
import com.css.demo.model.user.UserVO;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public IResult<UserVO> getUserById(String userId) {
        Optional<User> user = userDao.queryUserById(userId);
        if (user.isEmpty()) {
            return ResultBuilder.build(BaseMessage.NOT_FOUND);
        }
        return ResultBuilder.build(BaseMessage.SUCCESS, UserMapper.toUserVO(user.get()));
    }

    public Optional<User> createUser(UpsertUserRequest request) {
        if (Objects.isNull(request)){
            throw new BizException(BaseMessage.BAD_REQUEST_PARAM);
        }
        if (Strings.isNullOrEmpty(request.getName())
                || Strings.isNullOrEmpty(request.getPhone())){
            throw new BizException(BaseMessage.BAD_REQUEST_PARAM);
        }

        List<User> users = userDao.queryUsers(request.getName(), request.getPhone());
        if (!CollectionUtils.isEmpty(users)){
            throw new BizException(BaseMessage.USER_EXISTING);
        }

        User user = UserMapper.toUser(request);

        userDao.createUser(user);

        return Optional.ofNullable(user);
    }


    public Optional<User> updateUser(UpsertUserRequest request) {
        if (Objects.isNull(request)){
            throw new BizException(BaseMessage.BAD_REQUEST_PARAM);
        }
        if (Strings.isNullOrEmpty(request.getId())){
            throw new BizException(BaseMessage.BAD_REQUEST_PARAM);
        }

        Optional<User> user = userDao.queryUserById(request.getId());
        if (user.isEmpty()){
            throw new BizException(BaseMessage.NOT_FOUND);
        }

        User update = UserMapper.toUser(request);

        userDao.updateUser(update);

        return Optional.ofNullable(update);
    }

    public void deleteUserById(String userId) {
        if (Strings.isNullOrEmpty(userId)){
            throw new BizException(BaseMessage.BAD_REQUEST_PARAM);
        }

        Optional<User> user = userDao.queryUserById(userId);
        if (user.isEmpty()){
            throw new BizException(BaseMessage.NOT_FOUND);
        }
        userDao.deleteUserById(userId);
    }
}
