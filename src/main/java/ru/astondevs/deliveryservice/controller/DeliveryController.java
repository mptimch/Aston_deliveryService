package ru.astondevs.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.astondevs.deliveryservice.dto.order.OrderDto;
import ru.astondevs.deliveryservice.service.DeliveryService;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PutMapping("/{id}")
    public ResponseEntity<?> changeDeliveryStatus(@PathVariable("id") Long id) {
        boolean result = deliveryService.changeDeliveryStatus(id);
        if (result) {
            return ResponseEntity.status(200).body("");
        }
        return ResponseEntity.status(500).body("");
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> add (@RequestBody OrderDto dto) {
        deliveryService.save(dto);
        if (dto == null) {
            return ResponseEntity.status(500).body("Please insert correct dto");
        }
        return ResponseEntity.status(201).body(dto);
    }


}
