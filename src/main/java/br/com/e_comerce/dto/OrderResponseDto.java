package br.com.e_comerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime createdAt;
    private BigDecimal total;
    private List<OrderItemResponseDto> items;
}
