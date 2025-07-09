package com.uca.parcialfinalncapas.controller;

import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.dto.response.LoginResponse;
import com.uca.parcialfinalncapas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest rq) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(rq.getCorreo(), rq.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
