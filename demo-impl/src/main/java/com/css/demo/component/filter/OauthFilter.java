package com.css.demo.component.filter;

import com.css.demo.model.common.BaseMessage;
import com.css.demo.model.common.ResultBuilder;
import com.css.demo.utils.HttpUtils;
import com.css.demo.utils.JsonUtils;
import com.css.demo.utils.JwtUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Configuration
@Order(2)
public class OauthFilter extends OncePerRequestFilter {
    private static final List<String> PASS_URI = new ArrayList<>();

    static {
        PASS_URI.add("/actuator/prometheus");
        PASS_URI.add("/index.html");
        PASS_URI.add("/static/.*");
        PASS_URI.add("/public/.*");
        PASS_URI.add("/webjars/.*");
        PASS_URI.add("/swagger.*");

        PASS_URI.add("/user.*");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (regexUri(requestUri)) {
            request.getSession().setAttribute("test", "test");
            filterChain.doFilter(request, response);
            return;
        }

        String token = HttpUtils.getAuthorization(request);
        if (Strings.isNullOrEmpty(token)) {
            writeForbidResponse(response);
            return;
        }

        if (JwtUtils.isExpiration(token)) {
            log.info("Token is expired, token:{}", token);
            writeForbidResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void writeForbidResponse(HttpServletResponse response) {
        writeResponseWithJson(response, JsonUtils.serialize(ResultBuilder.build(BaseMessage.FORBID)));
    }

    protected void writeResponseWithJson(HttpServletResponse response, String responseObject) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.append(responseObject);
        } catch (IOException e) {
            log.error("responseOutWithJson error:", e);
        }
    }

    private Boolean regexUri(String uri) {
        if (CollectionUtils.isEmpty(OauthFilter.PASS_URI)) {
            return false;
        }
        for (String regex : OauthFilter.PASS_URI) {
            if (regex(regex, uri)) {
                return true;
            }
        }
        return false;
    }

    private Boolean regex(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }
}
