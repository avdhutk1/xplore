package com.avdhut.boot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is an example of a filter that is auto injected using @Component annotation
 * @Order annotation specifies the order of execution of filters in case of multiple filters
 * In this case, the filter is invoked for all URL's
 */

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req =  (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        logger.info("Request-Response filter being invoked for path {} and method {}", req.getRequestURI(), req.getMethod());

        filterChain.doFilter(servletRequest,servletResponse);

        logger.info("way back from Request-Response filter and content types is {}", res.getContentType());
    }
}
