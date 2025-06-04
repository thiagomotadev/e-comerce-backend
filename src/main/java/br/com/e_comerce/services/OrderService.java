package br.com.e_comerce.services;

import br.com.e_comerce.dto.*;
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
         */
        public OrderResponseDto createOrder(CreateOrderRequestDto dto) {
                User user = authSerice.getAuthenticatedUser();

                List<OrderItem> orderItems = new ArrayList<>();
                BigDecimal total = BigDecimal.ZERO;

                for (OrderItemRequestDto itemDto : dto.getItems()) {
                        if (itemDto.getQuantity() <= 0) {
                                throw new IllegalArgumentException("Quantidade inválida para o produto ID " + itemDto.getProductId());
                        }

                        Product product = productRepository.findById(itemDto.getProductId())
                                .orElseThrow(() -> new RuntimeException("Produto não encontrado: ID " + itemDto.getProductId()));

                        BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
                        total = total.add(itemTotal);

                        OrderItem orderItem = OrderItem.builder()
                                .product(product)
                                .quantity(itemDto.getQuantity())
                                .price(product.getPrice())
                                .build();

                        orderItems.add(orderItem);
                }

                Order order = Order.builder()
                        .createdAt(LocalDateTime.now())
                        .total(total)
                        .paymentMethod(dto.getPaymentMethod())
                        .user(user) //  Importante para evitar NullPointerException depois
                        .items(orderItems)
                        .build();

                // Associa os itens ao pedido
                for (OrderItem item : order.getItems()) {
                    item.setOrder(order);
                }

                order = orderRepository.save(order);

                List<OrderItemResponseDto> responseItems = orderItems.stream()
                        .map(item -> new OrderItemResponseDto(
                                item.getProduct().getName(),
                                item.getPrice(),
                                item.getQuantity()))
                        .toList();

                return new OrderResponseDto(
                        order.getId(),
                        order.getCreatedAt(),
                        order.getTotal(),
                        responseItems
                );
        }

        /**
         * Retorna o histórico de pedidos do usuário autenticado.
         */
        public List<OrderSummaryDto> getUserOrderHistory() {
                User user = authSerice.getAuthenticatedUser();
                List<Order> orders = orderRepository.findByUser(user);

                return orders.stream()
                        .map(order -> new OrderSummaryDto(
                                order.getId(),
                                order.getCreatedAt(),
                                order.getTotal()))
                        .toList();
        }

        /**
         * Retorna o histórico de pedidos de todos os usuários (admin).
         */
        public List<AdminOrderSummaryDto> getAdminUserOrderHistory() {
                List<Order> orders = orderRepository.findAll();

                List<AdminOrderSummaryDto> ordersSumary = new ArrayList<>();
                for (Order order : orders) {
                        var orderSumary = new AdminOrderSummaryDto(
                                order.getId(),
                                order.getUser(),
                                order.getTotal(),
                                order.getStatus(),
                                order.getCreatedAt());

                        ordersSumary.add(orderSumary);
                }

                return ordersSumary;
        }

        /**
         * Retorna os detalhes de um pedido, se o usuário for dono ou admin.
         */
        public OrderDetailDto getOrderById(Long orderId) {
                User currentUser = authSerice.getAuthenticatedUser();

                // Usa JOIN FETCH para garantir que order.getUser() não seja null
                Order order = orderRepository.findByIdWithUser(orderId)
                        .orElseThrow(() -> new RuntimeException("Pedido não encontrado: ID " + orderId));

                boolean isOwner = order.getUser().getId().equals(currentUser.getId());
                boolean isAdmin = currentUser.getRole().name().equals("ADMIN");

                if (!isOwner && !isAdmin) {
                        throw new RuntimeException("Acesso negado a este pedido.");
                }

                List<OrderItemResponseDto> items = order.getItems().stream()
                        .map(item -> new OrderItemResponseDto(
                                item.getProduct().getName(),
                                item.getPrice(),
                                item.getQuantity()))
                        .toList();

                return new OrderDetailDto(
                        order.getId(),
                        order.getCreatedAt(),
                        order.getTotal(),
                        order.getStatus(),
                        order.getPaymentMethod(),
                        items
                );
        }
}
