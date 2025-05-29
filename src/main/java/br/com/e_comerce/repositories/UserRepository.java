package br.com.e_comerce.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.e_comerce.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> { 
    Optional<User> findByEmail(String email);
 }
