package br.com.e_comerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.e_comerce.dto.LoginDto;
import br.com.e_comerce.dto.RegisterUserDto;
import br.com.e_comerce.dto.ResponseUserDto;
import br.com.e_comerce.security.JwtUtils;
import br.com.e_comerce.services.AuthSerice;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthSerice userService;

    @Autowired
    JwtUtils jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ResponseEntity<ResponseUserDto> register(@RequestBody RegisterUserDto request) {
        var result = this.userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));

        String token = jwtUtil.generateToken(loginDto.email());
        return ResponseEntity.ok(token);
    }
}
