package com.pudulabs.cloudrun.dataclean.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pudulabs.cloudrun.dataclean.config.multitenant.DataCleanTenantContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DataCleanInterceptor extends HandlerInterceptorAdapter {

    private static final String TENANT_HEADER_NAME = "X-TENANT-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String tenantId = request.getHeader(TENANT_HEADER_NAME);
        DataCleanTenantContext.setTenantId(tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        DataCleanTenantContext.clear();
    }
}