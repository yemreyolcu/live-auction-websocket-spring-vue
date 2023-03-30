package com.kartaca.kartacaoffer.interceptors;

import com.kartaca.kartacaoffer.config.security.jwt.services.JwtUtil;
import com.kartaca.kartacaoffer.config.security.userdetail.CustomUserDetails;
import com.kartaca.kartacaoffer.config.security.userdetail.CustomUserDetailsService;
import com.kartaca.kartacaoffer.entities.Token;
import com.kartaca.kartacaoffer.exceptions.JWTExpiredHandler;
import com.kartaca.kartacaoffer.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    final TokenService tokenService;
    final JwtUtil jwtUtil;
    final CustomUserDetailsService customUserDetailsService;

    public AuthInterceptor(TokenService tokenService, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Value("${refresh.expire.hours}")
    private Long expireHr;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getHeader("Authorization") != null) {
            String jwt = request.getHeader("Authorization").substring(7);
            String username = "";
            Token token;
            try {
                username = jwtUtil.extractUsername(jwt);
                token = tokenService.tokenReadByUsername(username);
            } catch (NullPointerException e) {
                throw new NullPointerException("Session expired");
            } catch (ExpiredJwtException e) {
                throw new JWTExpiredHandler("Session expired");
            }
            if (jwt.equals(token.getJwt())) {
                if (jwtUtil.tokenExpiredHours(jwt) < 6L) {
                    final CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    final String refreshJwt = jwtUtil.generateToken(userDetails, expireHr);
                    tokenService.tokenCreate(new Token(username, refreshJwt), expireHr);
                    response.addHeader("refreshToken", refreshJwt);
                    response.addHeader("Access-Control-Expose-Headers", "refreshToken");
                }
            } else {
                response.setStatus(401);
                return false;
            }
        } return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
