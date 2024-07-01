package ru.dmitryobuhkoff.orderservice.repository.unifier;

import org.springframework.stereotype.Component;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderFindDtoResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderUnifier implements Unifier<OrderEntity, List<OrderFindDtoResponse>>{
    @Override
    public OrderEntity unify(List<OrderFindDtoResponse> list) {
        OrderFindDtoResponse orderFindDtoResponse = list.get(0);
        OrderEntity order = orderFindDtoResponse.getOrderEntity();
        order.setOrderDetailEntities(new ArrayList<>());
        list.forEach(orderWithDetails -> {
            order.getOrderDetailEntities().add(orderWithDetails.getOrderDetailsEntity());
        });
        return order;
    }
}
