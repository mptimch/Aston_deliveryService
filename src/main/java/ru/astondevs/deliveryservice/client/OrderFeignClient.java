package ru.astondevs.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "orderClient", url = "http://localhost:9999/delivery/")
public interface OrderFeignClient {

    @PatchMapping("/{id}")
    void changeOrderStatusToCompleted(@PathVariable("id") Long id);
}
