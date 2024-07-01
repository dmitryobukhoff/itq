package ru.dmitryobuhkoff.orderservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderFindDtoResponse {
    private OrderEntity orderEntity;
    private OrderDetailsEntity orderDetailsEntity;
}
