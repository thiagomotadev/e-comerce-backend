package br.com.e_comerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.e_comerce.dto.LoginDto;
import br.com.e_comerce.dto.RegisterUserDto;
import br.com.e_comerce.dto.ResponseUserDto;
import br.com.e_comerce.entities.enums.Role;
import br.com.e_comerce.entities.user.User;
import br.com.e_comerce.repositories.UserRepository;
import br.com.e_comerce.security.JwtService;

import java.util.Arrays;

@Service
public class AuthSerice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseUserDto register(RegisterUserDto request) {

        if (!request.password().equals(request.confirmPassword())) {
            throw new RuntimeException("As senhas não coincidem.");
        }

        if (!isEmailDomainValid(request.email())) {
            throw new RuntimeException("Domínio de e-mail inválido. Verifique se escreveu corretamente.");
        }

        Boolean userExists = this.userRepository.existsByEmail(request.email());

        if(userExists) {
             throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        
        String hashedPassword = passwordEncoder.encode(request.password());

        var user = new User(
                request.name(),
                request.email(),
                hashedPassword,
                Role.USER);

        var userSaved = this.userRepository.save(user);

        return new ResponseUserDto(
                userSaved.getName(),
                userSaved.getEmail(),
                userSaved.getRole(),
                userSaved.getCreatedAt());
    }

    private boolean isEmailDomainValid(String email) {
        String[] acceptedDomains = {
                "gmail.com", "hotmail.com", "outlook.com", "yahoo.com"
        };

        return Arrays.stream(acceptedDomains)
                .anyMatch(domain -> email.toLowerCase().endsWith("@" + domain));
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Spring pega do UserDetails.getUsername()
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public String login(LoginDto request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.email(), request.password());

            authenticationManager.authenticate(authenticationToken);

            String token = jwtService.generateToken(request.email());
            return token;

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }
    }
}
