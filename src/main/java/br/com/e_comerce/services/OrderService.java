package br.com.e_comerce.services;

import br.com.e_comerce.dto.CreateOrderRequestDto;
import br.com.e_comerce.dto.OrderItemRequestDto;
import br.com.e_comerce.dto.OrderItemResponseDto;
import br.com.e_comerce.dto.OrderResponseDto;
import br.com.e_comerce.dto.OrderSummaryDto;
import br.com.e_comerce.entities.Order;
import br.com.e_comerce.entities.OrderItem;
import br.com.e_comerce.entities.Product;
import br.com.e_comerce.entities.user.User;
import br.com.e_comerce.repositories.OrderRepository;
import br.com.e_comerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AuthSerice authSerice;

    /**
     * Cria um novo pedido com base nos itens enviados pelo front-end.
     * Realiza validações, monta os itens, calcula o total e persiste o pedido no banco.
     *
     * @param dto DTO contendo os itens e método de pagamento
     * @return DTO com resumo do pedido criado
     */
    public OrderResponseDto createOrder(CreateOrderRequestDto dto) {
        // Obtém o usuário autenticado (requer login)
        User user = authSerice.getAuthenticatedUser();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // Processa cada item enviado no DTO
        for (OrderItemRequestDto itemDto : dto.getItems()) {
            // Valida a quantidade
            if (itemDto.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero para o produto ID " + itemDto.getProductId());
            }

            // Busca o produto pelo ID
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: ID " + itemDto.getProductId()));

            // Calcula o subtotal do item
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(itemTotal);

            // Cria um item de pedido (OrderItem)
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItems.add(orderItem);
        }

        // Cria a entidade do pedido principal (Order)
        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .total(total)
                .paymentMethod(dto.getPaymentMethod())
                .user(user)
                .items(orderItems) // associa os itens
                .build();

        // Associa cada item ao pedido principal (bidirecional)
        Order finalOrder = order;
        orderItems.forEach(item -> item.setOrder(finalOrder));

        // Salva no banco (cascata salva também os itens)
        order = orderRepository.save(order);

        // Converte os itens para DTO de resposta
        List<OrderItemResponseDto> responseItems = orderItems.stream()
                .map(item -> new OrderItemResponseDto(
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity()
                )).toList();

        // Retorna o resumo do pedido
        return new OrderResponseDto(
                order.getId(),
                order.getCreatedAt(),
                order.getTotal(),
                responseItems
        );
    }
    /**
     * Retorna o histórico de pedidos do usuário autenticado.
     * @return Lista de pedidos com dados resumidos (id, data, total)
     */
    public List<OrderSummaryDto> getUserOrderHistory() {
        // Obtém o usuário autenticado
        User user = authSerice.getAuthenticatedUser();

        // Busca todos os pedidos feitos por esse usuário
        List<Order> orders = orderRepository.findByUser(user);

        // Mapeia os pedidos para DTOs de resumo
        return orders.stream()
                .map(order -> new OrderSummaryDto(
                        order.getId(),
                        order.getCreatedAt(),
                        order.getTotal()
                )).toList();
    }
}
