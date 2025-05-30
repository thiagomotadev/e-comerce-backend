package br.com.e_comerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.e_comerce.dto.RegisterUserDto;
import br.com.e_comerce.dto.ResponseUserDto;
import br.com.e_comerce.entities.enums.Role;
import br.com.e_comerce.entities.user.User;
import br.com.e_comerce.repositories.UserRepository;

import java.util.Arrays;

@Service
public class AuthSerice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseUserDto register(RegisterUserDto request) {

        if (!request.password().equals(request.confirmPassword())) {
            throw new RuntimeException("As senhas não coincidem.");
        }

        if (!isEmailDomainValid(request.email())) {
            throw new RuntimeException("Domínio de e-mail inválido. Verifique se escreveu corretamente.");
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
}
