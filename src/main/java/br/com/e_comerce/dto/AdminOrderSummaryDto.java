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
    Long id;
    User user;
    BigDecimal total;
    OrderStatus status;
    LocalDateTime data;
}
