package br.com.e_comerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.e_comerce.entities.enums.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime createdAt;
    private BigDecimal total;
    private OrderStatus status;
    private List<OrderItemResponseDto> items;
}
