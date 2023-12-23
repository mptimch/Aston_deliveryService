package ru.astondevs.deliveryservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {

    private Long id;
    private String name;
    private String phone;
    private String address;
}
