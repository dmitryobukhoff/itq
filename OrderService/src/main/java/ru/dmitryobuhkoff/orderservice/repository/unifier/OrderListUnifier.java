package ru.dmitryobuhkoff.orderservice.repository.unifier;

import org.springframework.stereotype.Component;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderFindDtoResponse;

import java.util.*;

@Component
public class OrderListUnifier implements Unifier<List<OrderEntity>, List<OrderFindDtoResponse>>{

    private Map<OrderEntity, List<OrderDetailsEntity>> uuids = new HashMap<>();
    @Override
    public List<OrderEntity> unify(List<OrderFindDtoResponse> list) {
        uuids.clear();
        list.forEach(orderFindDtoResponse -> {
                    if(!uuids.containsKey(orderFindDtoResponse.getOrderEntity()))
                        uuids.put(orderFindDtoResponse.getOrderEntity(), new LinkedList<>());
                    uuids.get(orderFindDtoResponse.getOrderEntity()).add(orderFindDtoResponse.getOrderDetailsEntity());
                });

        uuids.keySet().forEach(order -> {
                    order.setOrderDetailEntities(uuids.get(order));
                });

        return new ArrayList<>(uuids.keySet());
    }

}
