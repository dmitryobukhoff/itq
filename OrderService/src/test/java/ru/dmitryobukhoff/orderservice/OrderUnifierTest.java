package ru.dmitryobukhoff.orderservice;

import org.junit.jupiter.api.Test;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderFindDtoResponse;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;
import ru.dmitryobuhkoff.orderservice.repository.unifier.OrderUnifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderUnifierTest {
    private final OrderUnifier orderUnifier = new OrderUnifier();

    @Test
    void testUnify(){
        UUID uuid = UUID.randomUUID();
        Date date = new Date();
        ArrayList<OrderDetailsEntity> details = new ArrayList<>(List.of(
                OrderDetailsEntity.builder()
                        .cost(1000.0)
                        .amount(1)
                        .article(123456)
                        .name("T-Shirt")
                        .build(),
                OrderDetailsEntity.builder()
                        .cost(250.0)
                        .amount(2)
                        .article(234567)
                        .name("Socks")
                        .build()
        ));

        OrderEntity orderEntity = OrderEntity.builder()
                .id(uuid)
                .date(date)
                .number("aaaaa")
                .recipient("Ivanov Ivan")
                .address("Moscow")
                .sum(1500.0)
                .delivery(Delivery.COURIER)
                .payment(Payment.CARD)
                .build();

        List<OrderFindDtoResponse> orderFindDtoResponseList = new ArrayList<>(List.of(
                new OrderFindDtoResponse(orderEntity, details.get(0)),
                new OrderFindDtoResponse(orderEntity, details.get(1))
        ));

        OrderEntity expectedOrderEntity = OrderEntity.builder()
                .id(uuid)
                .date(date)
                .number("aaaaa")
                .recipient("Ivanov Ivan")
                .address("Moscow")
                .sum(1500.0)
                .delivery(Delivery.COURIER)
                .payment(Payment.CARD)
                .orderDetailEntities(details)
                .build();

        OrderEntity response = orderUnifier.unify(orderFindDtoResponseList);

        assertEquals(response, expectedOrderEntity);
        assertEquals(response.getOrderDetailEntities().get(0), details.get(0));
        assertEquals(response.getOrderDetailEntities().get(1), details.get(1));
    }
}
