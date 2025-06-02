package br.com.e_comerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponseDto {
    private String productName;
    private BigDecimal price;
    private int quantity;
}
