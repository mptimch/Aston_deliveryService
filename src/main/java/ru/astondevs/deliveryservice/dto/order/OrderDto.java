package ru.astondevs.deliveryservice.dto.order;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private Long id;
    private String tgChatIdClient;
    private String addressClient;
    private List<GoodDto> goods = new ArrayList<>();
    private ShopDto shop;
}
