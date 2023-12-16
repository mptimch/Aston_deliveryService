package ru.astondevs.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.astondevs.deliveryservice.dto.enums.AddressType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "address_type")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "corpus")
    private int corpus;

    @Column(name = "house", nullable = false)
    private int house;

    @Column(name = "flat")
    private int flat;

    @Builder.Default
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<Delivery> deliveries = new ArrayList<>();
}
