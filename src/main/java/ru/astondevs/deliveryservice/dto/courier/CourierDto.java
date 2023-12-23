package ru.astondevs.deliveryservice.dto.courier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierDto {

    private UUID id;
    private String tgChatCourierId;
    private String name;
}
