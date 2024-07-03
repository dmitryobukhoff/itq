package ru.dmitryobukhoff.orderservice;

import org.junit.jupiter.api.Test;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderFindDtoResponse;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;
import ru.dmitryobuhkoff.orderservice.repository.unifier.OrderListUnifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderListUnifierTest {

    private final OrderListUnifier orderListUnifier = new OrderListUnifier();

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
                        .build(),
                OrderDetailsEntity.builder()
                        .cost(750.0)
                        .amount(1)
                        .article(345678)
                        .name("Jacket")
                        .build()
        ));

        OrderEntity orderEntity1 = OrderEntity.builder()
                .id(uuid)
                .number("aaaaa")
                .recipient("Ivanov Ivan")
                .address("Moscow")
                .date(date)
                .sum(1000.0)
                .delivery(Delivery.COURIER)
                .payment(Payment.CARD)
                .build();

        OrderEntity orderEntity2 = OrderEntity.builder()
                .id(uuid)
                .number("bbbbb")
                .recipient("Pavlov Pavel")
                .address("Izhevsk")
                .date(date)
                .sum(1250.0)
                .delivery(Delivery.PICKUP)
                .payment(Payment.CASH)
                .build();

        List<OrderFindDtoResponse> orderFindDtoResponseList = new ArrayList<>(List.of(
                new OrderFindDtoResponse(orderEntity1, details.get(0)),
                new OrderFindDtoResponse(orderEntity2, details.get(1)),
                new OrderFindDtoResponse(orderEntity2, details.get(2))
        ));

        List<OrderEntity> response = orderListUnifier.unify(orderFindDtoResponseList);


        assertEquals(response.size(), 2);
    }
}
