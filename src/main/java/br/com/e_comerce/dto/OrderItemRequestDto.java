package br.com.e_comerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {
    private Long productId;
    private int quantity;
}
