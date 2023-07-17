package com.css.demo.utils;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import static com.css.demo.model.common.Constants.AUTHORIZATION;
import static com.css.demo.model.common.Constants.COOKIE_INFO;

@Slf4j
public class HttpUtils {

    public static String getCookieInfo(HttpServletRequest request) {
        return CookieUtils.getValueByName(request, COOKIE_INFO);
    }

    public static String getAuthorization(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION)
                .replace("Bearer", "")
                .trim();
        return Strings.isNullOrEmpty(token) ? "" : token;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
