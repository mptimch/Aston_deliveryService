package ru.astondevs.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "courier_id", nullable = false)
    private UUID courierId;

    @Column(name = "tg_chat_id", nullable = false)
    private String tgChatId;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @OneToMany(mappedBy = "", fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    @Column(name = "accept_time", nullable = false)
    private LocalDateTime deliveryAcceptTime;
}
