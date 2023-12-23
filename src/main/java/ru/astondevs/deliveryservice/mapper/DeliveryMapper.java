package ru.astondevs.deliveryservice.mapper;

import lombok.RequiredArgsConstructor;
import ru.astondevs.deliveryservice.client.CourierFeignClient;
import ru.astondevs.deliveryservice.client.OrderFeignClient;

@RequiredArgsConstructor
public class DeliveryMapper {

    private final OrderFeignClient orderClient;
    private final CourierFeignClient courierClient;



}
