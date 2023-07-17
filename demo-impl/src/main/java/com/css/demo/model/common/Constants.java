package com.css.demo.model.common;

import java.util.HashMap;
import java.util.Map;

public interface Constants {

    String CHARSET = "UTF-8";
    String AES_PWD = "HICTO";
    Integer TOKEN_EXPIRE_DAYS = 7;
    String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String TIME_FORMAT = "HH:mm:ss";
    String DATE_FORMAT = "yyyy-MM-dd";

    Map<String, String> TEST_MAP = new HashMap<>() {
        {
            put("Test", "Test");
        }
    };

    String COOKIE_INFO = "userInfo";

    String AUTHORIZATION = "Authorization";
}
