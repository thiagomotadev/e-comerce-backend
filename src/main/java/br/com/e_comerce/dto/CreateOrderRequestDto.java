package br.com.e_comerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequestDto {
    private List<OrderItemRequestDto> items;
    private String paymentMethod;
}
