package com.infinity.department.utils;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.opentelemetry.context.Context.current;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserContextFilter implements Filter {
    private static final Logger LOGGER = log;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        UserContext userContext = UserContextHolder.getContext();
        userContext.setCorrelationId(userContext.getCorrelationId());
        userContext.setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        userContext.setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        userContext.setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));
        userContext.setDepartmentId(httpServletRequest.getHeader(UserContext.DEPARTMENT_ID));
        userContext.setEmployeeId(httpServletRequest.getHeader(UserContext.EMPLOYEE_ID));

        LOGGER.debug("UserContextFilter Correlation id: {}", userContext.getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
