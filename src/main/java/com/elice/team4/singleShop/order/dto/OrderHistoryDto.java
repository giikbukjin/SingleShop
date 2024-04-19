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
    private Status status;

    public enum Status {
        취소,

        배송준비중,

        배송중,

        배송완료
    }
}
