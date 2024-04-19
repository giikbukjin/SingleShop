package com.elice.team4.singleShop.order.dto;

import com.elice.team4.singleShop.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_orders")
public class OrderHistoryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    private String summaryTitle;

    @Enumerated(EnumType.STRING)
    private OrderHistoryDto.Status status;

    public enum Status {
        CANCEL,
        DELIVERY_READY,
        DELIVERING,
        DELIVERY_COMPLETE
    }
}
