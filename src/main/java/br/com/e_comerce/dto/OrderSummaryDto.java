package br.com.e_comerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSummaryDto {

    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal total;

    // Construtor
    public OrderSummaryDto(Long id, LocalDateTime createdAt, BigDecimal total) {
        this.id = id;
        this.createdAt = createdAt;
        this.total = total;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime
    getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
