package br.com.e_comerce.services;

import br.com.e_comerce.dto.RegisterUserDto;
import br.com.e_comerce.dto.ResponseUserDto;

public interface IUserService {

    public ResponseUserDto registerUser(RegisterUserDto request);

}
