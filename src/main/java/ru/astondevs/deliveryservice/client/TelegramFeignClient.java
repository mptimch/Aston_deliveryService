package ru.astondevs.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.astondevs.deliveryservice.dto.message.MessageDto;

@FeignClient(value = "telegramClient", url = "http://localhost:8080/")
public interface TelegramFeignClient {

    @PutMapping("/{id}")
    void sendMessageToTgChatCourier(@PathVariable("id") String id, @RequestBody MessageDto messageDto);

    @PutMapping("/{id}")
    void sendMessageToTgChatClient(@PathVariable("id") String id, @RequestBody MessageDto messageDto);
}
