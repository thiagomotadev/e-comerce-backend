package br.com.e_comerce.controllers;

import br.com.e_comerce.dto.CreateOrderRequestDto;
import br.com.e_comerce.dto.OrderResponseDto;
import br.com.e_comerce.dto.OrderSummaryDto;
import br.com.e_comerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Endpoint para criar um novo pedido.
     * Requer que o usuário esteja autenticado (validação feita no service).
     *
     * @param dto Dados do pedido recebidos do front-end (lista de itens e método de pagamento)
     * @return Um resumo do pedido criado: ID, data, total e lista de itens.
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto dto) {
        OrderResponseDto response = orderService.createOrder(dto);
        return ResponseEntity.ok(response);
    }
    /**
     * Retorna o histórico de pedidos do usuário autenticado.
     * @return Lista de pedidos com dados resumidos.
     */
    @GetMapping
    public ResponseEntity<List<OrderSummaryDto>> getOrderHistory() {
        List<OrderSummaryDto> history = orderService.getUserOrderHistory();
        return ResponseEntity.ok(history);
    }

}
