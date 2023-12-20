package ru.astondevs.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "courierClient", url = "http://localhost:8080/")
public interface CourierFeignClient {
}
