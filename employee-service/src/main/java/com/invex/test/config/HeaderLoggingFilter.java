package com.invex.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Filtro para registrar los headers de cada peticion en los logs.
 */
@Component
public class HeaderLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HeaderLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Enumeration<String> headerNames = httpRequest.getHeaderNames();
            StringBuilder headersLog = new StringBuilder("Request Headers: ");
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headersLog.append(headerName).append("=").append(httpRequest.getHeader(headerName)).append(", ");
            }
            logger.info(headersLog.toString());
        }
        chain.doFilter(request, response);
    }
}
