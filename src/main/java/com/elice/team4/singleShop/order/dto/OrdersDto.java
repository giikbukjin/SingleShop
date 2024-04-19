package com.elice.team4.singleShop.order.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin_orders")
public class OrdersDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalPrice;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    private String summaryTitle;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CANCEL,
        DELIVERY_READY,
        DELIVERING,
        DELIVERY_COMPLETE
    }
}