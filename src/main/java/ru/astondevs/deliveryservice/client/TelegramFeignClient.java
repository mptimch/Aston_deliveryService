package ru.astondevs.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "telegramClient", url = "http://localhost:8080/")
public interface TelegramFeignClient {

    @PutMapping("/id")
    void  sendMessageToTgChat(@PathVariable("id") String id);
}
