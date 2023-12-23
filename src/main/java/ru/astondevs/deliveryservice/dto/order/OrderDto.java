package ru.astondevs.deliveryservice.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String tgChatIdClient;
    private String addressClient;
    private List<GoodDto> goods = new ArrayList<>();
    private ShopDto shop;

    public String getNameAndCountGood() {
        String result = "";
        for (GoodDto dto : goods) {
            String name = dto.getName();
            String count = String.valueOf(dto.getQuantity());
            result += name + ": " + count;
        }
        return result;
    }
}
