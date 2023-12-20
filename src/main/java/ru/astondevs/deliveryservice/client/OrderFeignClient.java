package ru.astondevs.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "orderClient", url = "http://localhost:8080/")
public interface OrderFeignClient {
}
