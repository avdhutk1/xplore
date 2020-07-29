package com.avdhut.boot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This is an example of a filter that is injected using Java config
 * The FilterRegistrationBean method returns the bean
 * Order of execution can also be specified in the method
 * See ModuleConfig class for more details
 * Note that there is no @Component annotation. It is not required if registered via Java config
 */



public class CreationAuditFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(CreationAuditFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req =  (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        logger.info("Product creation filter is being audited for path {} and method {}", req.getRequestURI(), req.getMethod());

        filterChain.doFilter(servletRequest,servletResponse);

        logger.info("way back from creation filter and content types is {}", res.getContentType());
    }
}
