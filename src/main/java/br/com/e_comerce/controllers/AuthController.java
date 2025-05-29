package br.com.e_comerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.e_comerce.dto.RegisterUserDto;
import br.com.e_comerce.dto.ResponseUserDto;
import br.com.e_comerce.services.AuthSerice;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthSerice userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> register(@RequestBody RegisterUserDto request) {
        var result = this.userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
