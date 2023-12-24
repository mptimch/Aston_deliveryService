package ru.astondevs.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.astondevs.deliveryservice.client.CourierFeignClient;
import ru.astondevs.deliveryservice.client.OrderFeignClient;
import ru.astondevs.deliveryservice.client.TelegramFeignClient;
import ru.astondevs.deliveryservice.dto.courier.CourierDto;
import ru.astondevs.deliveryservice.dto.enums.DeliveryStatus;
import ru.astondevs.deliveryservice.dto.message.MessageDto;
import ru.astondevs.deliveryservice.dto.order.OrderDto;
import ru.astondevs.deliveryservice.entity.Delivery;
import ru.astondevs.deliveryservice.exception.*;
import ru.astondevs.deliveryservice.mapper.DeliveryMapper;
import ru.astondevs.deliveryservice.repository.DeliveryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final CourierFeignClient courierClient;
    private final OrderFeignClient orderClient;
    private final TelegramFeignClient telegramClient;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    @Transactional
    public boolean save(OrderDto orderDto) {
        if (orderDto == null) {
            throw new NotFoundOrderException("Order not found");
        }

        validateAlreadyExists(orderDto);

        CourierDto courierDto = courierClient.findFreeCourier();

        if (courierDto == null) {
            throw new NotFoundCourierException("Courier not found");
        }

        Delivery delivery = deliveryMapper.createDelivery(orderDto, courierDto);

        Delivery savedDeliveryEntity = deliveryRepository.save(delivery);

        sendMessageToTgChatIdForCourier(orderDto, savedDeliveryEntity);
        sendMessageToTgChatIdForClient(orderDto, savedDeliveryEntity);

        return true;
    }

    @Transactional
    public void changeDeliveryStatus(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundModelException(String.format("Delivery with id = %s not found ", deliveryId)));

        try {
            changeDeliveryStatusToCompleted(deliveryId);
            changeOrderStatusToCompleted(delivery.getOrderId());
        } catch (Exception e) {
            throw new DeliveryStatusChangeException(String.format("Failed to change delivery status, deliveryId = %s", deliveryId));
        }
    }

    private void changeDeliveryStatusToCompleted(Long deliveryId) {
        deliveryRepository.changeDeliveryStatus(DeliveryStatus.COMPLETED, deliveryId);
    }

    private void changeOrderStatusToCompleted(Long orderId) {
        orderClient.changeOrderStatusToCompleted(orderId);
    }

    private void sendMessageToTgChatIdForClient(OrderDto orderDto, Delivery delivery) {
        if (delivery == null) {
            throw new NotFoundModelException("Delivery not found");
        }
        String messageForClient = createMessageForClient(orderDto, delivery);
        telegramClient.sendMessageToTgChatClient(delivery.getTgChatClientId(), new MessageDto(messageForClient));
    }

    private void sendMessageToTgChatIdForCourier(OrderDto orderDto, Delivery delivery) {
        if (delivery == null) {
            throw new NotFoundModelException("Delivery not found");
        }
        String messageForCourier = createMessageForCourier(orderDto, delivery);
        telegramClient.sendMessageToTgChatCourier(delivery.getTgChatCourierId(), new MessageDto(messageForCourier));
    }

    private String createMessageForCourier(OrderDto orderDto, Delivery delivery) {
        String shopAddress = orderDto.getShop().getAddress();
        String shopPhone = orderDto.getShop().getPhone();
        String addressClient = orderDto.getAddressClient();
        String nameAndCountGood = orderDto.getGoodsNamesAndQuantities(orderDto.getGoods());
        String deliveryStatus = delivery.getDeliveryStatus().name();
        String tgChatIdClient = orderDto.getTgChatIdClient();

        return String.format("Address shop: %s, Address client: %s, Phone shop: %s, Good and count good: %s, Priority: %s, Client tgChatId: %s",
                shopAddress, addressClient, shopPhone, nameAndCountGood, deliveryStatus, tgChatIdClient
        );
    }

    private String createMessageForClient(OrderDto orderDto, Delivery delivery) {
        String addressClient = orderDto.getAddressClient();
        String phoneShop = orderDto.getShop().getPhone();
        String nameAndCountGood = orderDto.getGoodsNamesAndQuantities(orderDto.getGoods());
        String tgCourierChatId = delivery.getTgChatCourierId();

        return String.format("Address client = %s, Phone shop = %s, Good and count good = %s, Courier tgCourierChatId : %s",
                addressClient, phoneShop, nameAndCountGood, tgCourierChatId
        );
    }

    private void validateAlreadyExists(OrderDto orderDto) {
        Optional<Delivery> deliveryByOrderId = deliveryRepository.findByOrderId(orderDto.getId());

        if (deliveryByOrderId.isPresent()) {
            throw new DuplicateException(String.format("Order with id = %s already exist", orderDto.getId()));
        }
    }
}
