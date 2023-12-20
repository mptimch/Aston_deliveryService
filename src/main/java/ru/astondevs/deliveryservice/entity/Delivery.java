package ru.astondevs.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.astondevs.deliveryservice.dto.enums.DeliveryPriority;
import ru.astondevs.deliveryservice.dto.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "delivery_priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryPriority deliveryPriority;

    @Column(name = "courier_id", nullable = false)
    private UUID courierId;

    @Column(name = "tg_chat_id", nullable = false)
    private String tgChatId;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "delivery_time_creation", nullable = false)
    private LocalDateTime deliveryTimeCreation;
}
