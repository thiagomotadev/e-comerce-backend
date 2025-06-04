package br.com.e_comerce.repositories;

import br.com.e_comerce.entities.Order;
import br.com.e_comerce.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Retorna todos os pedidos de um usuário específico
    List<Order> findByUser(User user);

    // Retorna o pedido com o usuário já carregado (evita LazyInitializationException)
    @Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.id = :id")
    Optional<Order> findByIdWithUser(@Param("id") Long id);
}
