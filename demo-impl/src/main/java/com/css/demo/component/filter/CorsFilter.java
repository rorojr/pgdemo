package com.css.demo.component.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Slf4j
@Configuration
@Order(1)
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String origin = request.getHeader("Origin");
        if (StringUtils.isNotBlank(origin)) {
            URL url = new URL(origin);
            String port = url.getPort() == -1 ? "" : ":" + url.getPort();
            String uri = url.getProtocol() + "://" + url.getHost() + port;
            response.addHeader("Access-Control-Allow-Origin", uri);
        }
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,_za_sso_session_,x-requested-with,client-type,Cookie");
        response.addHeader("Access-Control-Max-Age", "1800");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(200);
        } else {
            filterChain.doFilter(request, response);
        }
    }

}

