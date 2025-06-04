package br.com.e_comerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.e_comerce.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal total;
    private OrderStatus status;
    private String paymentMethod;
    private List<OrderItemResponseDto> items;
}
