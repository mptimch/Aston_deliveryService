package ru.astondevs.deliveryservice.mapper;

import org.springframework.stereotype.Component;
import ru.astondevs.deliveryservice.dto.courier.CourierDto;
import ru.astondevs.deliveryservice.dto.enums.DeliveryPriority;
import ru.astondevs.deliveryservice.dto.enums.DeliveryStatus;
import ru.astondevs.deliveryservice.dto.order.OrderDto;
import ru.astondevs.deliveryservice.entity.Delivery;

import java.time.LocalDateTime;

@Component
public class DeliveryMapper {

    public Delivery createDelivery(OrderDto orderDto, CourierDto courierDto) {
        Delivery delivery = new Delivery();

        delivery.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);
        delivery.setDeliveryPriority(DeliveryPriority.MEDIUM);
        delivery.setDeliveryTimeCreation(LocalDateTime.now());
        delivery.setOrderId(orderDto.getId());
        delivery.setCourierId(courierDto.getId());
        delivery.setTgChatClientId(orderDto.getTgChatIdClient());
        delivery.setTgChatCourierId(courierDto.getTgChatCourierId());
        return delivery;
    }
}
