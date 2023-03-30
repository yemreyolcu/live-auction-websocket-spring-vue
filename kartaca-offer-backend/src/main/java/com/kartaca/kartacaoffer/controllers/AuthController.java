package com.kartaca.kartacaoffer.controllers;


import com.kartaca.kartacaoffer.config.security.jwt.dto.AuthRequest;
import com.kartaca.kartacaoffer.dto.UserDto;
import com.kartaca.kartacaoffer.entities.User;
import com.kartaca.kartacaoffer.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final HttpServletRequest request;
    final ModelMapper modelMapper;
    final AuthService authService;

    public AuthController(HttpServletRequest request, ModelMapper modelMapper, AuthService authService) {
        this.request = request;
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody User user) {
        return new ResponseEntity<>(modelMapper.map(authService.register(user), UserDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logged-in-user")
    public ResponseEntity<?> getLoggedInUser() {
        return new ResponseEntity<>(modelMapper.map(authService.loggedInUser(), UserDto.class), HttpStatus.OK);
    }
}
