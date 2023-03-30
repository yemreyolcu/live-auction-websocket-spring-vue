package com.kartaca.kartacaoffer.services;

import com.kartaca.kartacaoffer.config.security.jwt.dto.AuthRequest;
import com.kartaca.kartacaoffer.config.security.jwt.dto.AuthResponse;
import com.kartaca.kartacaoffer.config.security.jwt.services.JwtUtil;
import com.kartaca.kartacaoffer.config.security.userdetail.CustomUserDetails;
import com.kartaca.kartacaoffer.config.security.userdetail.CustomUserDetailsService;
import com.kartaca.kartacaoffer.entities.Token;
import com.kartaca.kartacaoffer.entities.User;
import com.kartaca.kartacaoffer.exceptions.SQLExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;


@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;
    private final TokenService tokenService;
    private final UserService userService;

    @Value("${expire.hours}")
    private Long expireHr;

    public AuthService(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, HttpServletRequest request, TokenService tokenService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.request = request;
        this.tokenService = tokenService;
        this.userService = userService;
    }


    public AuthResponse login(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Username or password is wrong", ex);
        }
        final CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt_token = jwtUtil.generateToken(userDetails, expireHr);
        tokenService.tokenCreate(new Token(authRequest.getUsername(), jwt_token), expireHr);
        return new AuthResponse(jwt_token, userDetails.getUser().getId(), userDetails.getUser().getUsername());
    }

    public User register(User user) {
        User addedUser;
        try {
            addedUser = userService.createUser(user);
        } catch (DataIntegrityViolationException ex) {
            throw new SQLExceptionHandler("This user already exists");
        } return addedUser;
    }

    public User loggedInUser() {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtUtil.extractUsername(token);
        return userService.userRetrieveByUsername(username);
    }

    public void logout() {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtUtil.extractUsername(token);
        tokenService.tokenDeleteByUsername(username);
    }


}
