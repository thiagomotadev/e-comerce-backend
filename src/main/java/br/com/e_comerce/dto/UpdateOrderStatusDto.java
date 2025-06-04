package br.com.e_comerce.dto;

import br.com.e_comerce.entities.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusDto {

    @NotNull(message = "O status não pode ser nulo.")
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
