package br.com.e_comerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.e_comerce.entities.enums.OrderStatus;
import br.com.e_comerce.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminOrderSummaryDto {

    private Long id; // ID do pedido
    private User user; // Usuário que fez o pedido
    private BigDecimal total; // Valor total
    private OrderStatus status; // Status do pedido
    private LocalDateTime createdAt; // Data de criação
}
