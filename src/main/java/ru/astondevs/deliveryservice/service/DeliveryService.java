package ru.astondevs.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.deliveryservice.client.CourierFeignClient;
import ru.astondevs.deliveryservice.client.OrderFeignClient;
import ru.astondevs.deliveryservice.client.TelegramFeignClient;
import ru.astondevs.deliveryservice.dto.courier.CourierDto;
import ru.astondevs.deliveryservice.dto.enums.DeliveryPriority;
import ru.astondevs.deliveryservice.dto.enums.DeliveryStatus;
import ru.astondevs.deliveryservice.dto.message.MessageDto;
import ru.astondevs.deliveryservice.dto.order.OrderDto;
import ru.astondevs.deliveryservice.entity.Delivery;
import ru.astondevs.deliveryservice.exception.NotFoundModelException;
import ru.astondevs.deliveryservice.repository.DeliveryRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final CourierFeignClient courierClient;
    private final OrderFeignClient orderClient;
    private final TelegramFeignClient telegramClient;
    private final DeliveryRepository deliveryRepository;

    // Этот метод тоже сделал boolean, он при неудаче возвращает false. Чтоб в контроллере
    // потом возвращать другую ошибку
    @Transactional
    public boolean save(OrderDto orderDto) {
        try {
            CourierDto courierDto = courierClient.findFreeCourier();
            Delivery delivery = new Delivery();

            delivery.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);
            delivery.setDeliveryPriority(DeliveryPriority.MEDIUM);
            delivery.setDeliveryTimeCreation(LocalDateTime.now());
            delivery.setOrderId(orderDto.getId());
            delivery.setCourierId(courierDto.getId());
            delivery.setTgChatClientId(orderDto.getTgChatIdClient());
            delivery.setTgChatCourierId(courierDto.getTgChatCourierId());

            Delivery entity = deliveryRepository.save(delivery);
            sendMessageToTgChatIdForCourier(orderDto, entity, delivery);
            sendMessageToTgChatIdForClient(orderDto, delivery);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean changeDeliveryStatus(Long deliveryId) {
        Delivery entity = deliveryRepository.findById(deliveryId).orElseThrow(
                () -> new NotFoundModelException(String.format("Delivery with id = %s not found ",
                        deliveryId))
        );
        try {
            deliveryRepository.changeDeliveryStatus(DeliveryStatus.COMPLETED, deliveryId);
            orderClient.changeOrderStatusToCompleted(entity.getOrderId());
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    private void sendMessageToTgChatIdForCourier(OrderDto orderDto, Delivery entity, Delivery delivery) {
        MessageDto messageDtoForCourier = new MessageDto();
        messageDtoForCourier.setMessage(String.format("Address shop : %s, Address client = %s, Phone shop = %s, Good and count good = %s," +
                        "  Priority = %s,   Client tgChatId : %s", orderDto.getShop().getAddress(), orderDto.getAddressClient(), orderDto.getShop().getPhone(),
                orderDto.getNameAndCountGood(), entity.getDeliveryStatus(), orderDto.getTgChatIdClient()
        ));
        telegramClient.sendMessageToTgChatCourier(delivery.getTgChatCourierId(), messageDtoForCourier);
    }

    private void sendMessageToTgChatIdForClient(OrderDto orderDto, Delivery delivery) {
        MessageDto messageDtoForClient = new MessageDto();
        messageDtoForClient.setMessage(String.format("Address client = %s, Phone shop = %s, Good and count good = %s," +
                        " Courier tgCourierChatId : %s", orderDto.getAddressClient(), orderDto.getShop().getPhone(),
                orderDto.getNameAndCountGood(), delivery.getTgChatCourierId()
        ));
        telegramClient.sendMessageToTgChatClient(delivery.getTgChatClientId(), messageDtoForClient);
    }
}
