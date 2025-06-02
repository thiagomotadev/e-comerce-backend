package br.com.e_comerce.repositories;

import br.com.e_comerce.entities.Order;
import br.com.e_comerce.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
