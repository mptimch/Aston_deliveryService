package ru.astondevs.deliveryservice.dto.delivery;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record DeliveryDtoCreate(
        @NotBlank(message = "Enter order id")
        UUID orderId,
        String comment,
        @NotBlank(message = "Enter courier id")
        UUID courierId,
        @NotBlank(message = "Enter tg chat id")
        String tgChatId) {
}
