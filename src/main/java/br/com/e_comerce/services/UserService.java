package br.com.e_comerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.e_comerce.dto.RegisterUserDto;
import br.com.e_comerce.dto.ResponseUserDto;
import br.com.e_comerce.entities.enums.Role;
import br.com.e_comerce.entities.user.User;
import br.com.e_comerce.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseUserDto registerUser(RegisterUserDto request) {

        if (request.password().equals(request.comfirmPassword())) {
            throw new RuntimeException("This email already exists.");
        }

        var user = new User(
                request.name(),
                request.email(),
                request.password(),
                Role.USER);

        var userSaved = this.userRepository.save(user);

        return new ResponseUserDto(
                userSaved.getName(),
                userSaved.getEmail(),
                userSaved.getRole(),
                userSaved.getCreatedAt());
    }

}
