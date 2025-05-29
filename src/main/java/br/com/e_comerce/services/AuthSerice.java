package br.com.e_comerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.e_comerce.dto.RegisterUserDto;
import br.com.e_comerce.dto.ResponseUserDto;
import br.com.e_comerce.entities.enums.Role;
import br.com.e_comerce.entities.user.User;
import br.com.e_comerce.repositories.UserRepository;

@Service
public class AuthSerice {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseUserDto register(RegisterUserDto request) {

        if (!request.password().equals(request.confirmPassword())) {
            throw new RuntimeException("The passwords not is equals.");
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

}
