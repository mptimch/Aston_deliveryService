package ru.astondevs.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.astondevs.deliveryservice.dto.message.MessageDto;
import ru.astondevs.deliveryservice.dto.order.OrderDto;

import java.util.List;

@FeignClient(value = "orderClient", url = "http://localhost:8080/")
public interface OrderFeignClient {

    @PutMapping("/{id}")
    void changeOrderStatusToCompleted(@PathVariable("id") Long id);
}
