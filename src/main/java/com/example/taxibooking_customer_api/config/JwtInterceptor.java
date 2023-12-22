package com.example.taxibooking_customer_api.config;

import com.example.taxibooking_customer_api.dto.RequestMetaDTO;
import com.example.taxibooking_customer_api.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    RequestMetaDTO requestMetaDTO;

    public JwtInterceptor(RequestMetaDTO requestMetaDTO){
        this.requestMetaDTO = requestMetaDTO;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("authorization");

        if (
                !(request.getRequestURI().contains("customer/register/"))
        ){
            Claims claims = jwtUtil.verifyAccessToken(authorization);

            requestMetaDTO.setCustomerId(Integer.parseInt(claims.getIssuer()));
            requestMetaDTO.setName(claims.get("username").toString());
            requestMetaDTO.setMobile(claims.get("mobile").toString());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
