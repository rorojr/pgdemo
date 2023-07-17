package com.css.demo.utils;

import com.google.common.base.Strings;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CookieUtils {

    public static List<Cookie> getCookiesByName(HttpServletRequest request, String name) {
        List<Cookie> cookie = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    cookie.add(c);
                }
            }
        }
        return cookie;
    }

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    cookie = c;
                    break;
                }
            }
        }
        return cookie;
    }

    public static String getValueByName(HttpServletRequest request, String name) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    cookie = c;
                    break;
                }
            }
        }

        return cookie != null ? cookie.getValue() : "";
    }

    public static Cookie addCookie(HttpServletRequest request,
                                   HttpServletResponse response,
                                   String name,
                                   String value,
                                   Integer expiry,
                                   String domain) {
        Cookie cookie = new Cookie(name, value);
        //cookie.setSecure(true);
        //cookie.setHttpOnly(true);
        if (expiry != null) {
            cookie.setMaxAge(expiry);
        }

        if (!Strings.isNullOrEmpty(domain)) {
            cookie.setDomain(domain);
        } else {
            cookie.setDomain(getDomain(request));
        }

        String ctx = request.getContextPath();
        cookie.setPath(Strings.isNullOrEmpty(ctx) ? "/" : ctx);
        response.addCookie(cookie);
        return cookie;
    }

    public static Cookie addCookie(HttpServletRequest request,
                                   HttpServletResponse response,
                                   String name,
                                   String value,
                                   String domain) {
        Cookie cookie = new Cookie(name, value);
        if (!Strings.isNullOrEmpty(domain)) {
            cookie.setDomain(domain);
        }

        String ctx = request.getContextPath();
        cookie.setPath(Strings.isNullOrEmpty(ctx) ? "/" : ctx);
        response.addCookie(cookie);
        return cookie;
    }

    public static void cancelCookie(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String name) {
        List<Cookie> cookie = getCookiesByName(request, name);
        for (Cookie ck : cookie) {
            ck.setMaxAge(0);
            response.addCookie(ck);
        }

    }

    private static String getDomain(HttpServletRequest request) {
        String requestURI = request.getRequestURL().toString();
        int httpIndex = requestURI.indexOf("://") + 3;
        int contentPathIndex = requestURI.indexOf("/", httpIndex);
        if (contentPathIndex < 0) {
            contentPathIndex = requestURI.length();
        }

        String domainPort = requestURI.substring(httpIndex, contentPathIndex);
        return domainPort.contains(":") ? domainPort.substring(0, domainPort.indexOf(":")) : domainPort;
    }

    public static void cancelCookieWithDomain(HttpServletRequest request,
                                              HttpServletResponse response,
                                              String name,
                                              String domain) {
        List<Cookie> cookie = getCookiesByName(request, name);
        for (Cookie ck : cookie) {
            ck.setMaxAge(0);
            ck.setDomain(domain);
            response.addCookie(ck);
        }
    }
}
