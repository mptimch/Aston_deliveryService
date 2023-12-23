package ru.astondevs.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "orderClient", url = "http://localhost:8080/")
public interface OrderFeignClient {

    @PutMapping("/{id}")
    void changeOrderStatusToCompleted(@PathVariable("id") Long id);
}
