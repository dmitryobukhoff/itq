package ru.dmitryobukhoff.orderservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dmitryobuhkoff.orderservice.mapper.OrderDetailsMapper;
import ru.dmitryobuhkoff.orderservice.mapper.OrderMapper;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderDetailsCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDetailsDtoResponse;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDtoResponse;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {

    @Mock
    private OrderDetailsMapper orderDetailsMapper;

    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    void testFromEntityToDtoResponse(){

        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .cost(750.0)
                .amount(2)
                .name("T-Shirt")
                .article(123456)
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .recipient("Ivanov Ivan")
                .address("Moscow")
                .sum(1500.0)
                .delivery(Delivery.COURIER)
                .payment(Payment.CARD)
                .orderDetailEntities(new ArrayList<>(List.of(orderDetailsEntity)))
                .build();

        OrderDetailsDtoResponse expectedOrderDetailsDtoResponse = OrderDetailsDtoResponse.builder()
                .cost(750.0)
                .amount(2)
                .name("T-Shirt")
                .article(123456)
                .build();


        OrderDtoResponse expectedOrderDtoResponse = OrderDtoResponse.builder()
                .recipient("Ivanov Ivan")
                .address("Moscow")
                .sum(1500.0)
                .delivery(Delivery.COURIER)
                .payment(Payment.CARD)
                .build();

        when(orderDetailsMapper.fromEntityToDtoResponse(any(OrderDetailsEntity.class))).thenReturn(expectedOrderDetailsDtoResponse);

        OrderDtoResponse orderDtoResponse = orderMapper.fromEntityToDtoResponse(orderEntity);

        assertEquals(expectedOrderDtoResponse.getRecipient(), orderDtoResponse.getRecipient());
        assertEquals(expectedOrderDtoResponse.getAddress(), orderDtoResponse.getAddress());
        assertEquals(expectedOrderDtoResponse.getSum(), orderDtoResponse.getSum());
        assertEquals(expectedOrderDtoResponse.getDelivery(), orderDtoResponse.getDelivery());
        assertEquals(expectedOrderDtoResponse.getPayment(), orderDtoResponse.getPayment());
        assertEquals(expectedOrderDetailsDtoResponse.getArticle(), orderDtoResponse.getOrderDetailsDtoResponse().get(0).getArticle());
        assertEquals(expectedOrderDetailsDtoResponse.getAmount(), orderDtoResponse.getOrderDetailsDtoResponse().get(0).getAmount());
        assertEquals(expectedOrderDetailsDtoResponse.getCost(), orderDtoResponse.getOrderDetailsDtoResponse().get(0).getCost());
        assertEquals(expectedOrderDetailsDtoResponse.getName(), orderDtoResponse.getOrderDetailsDtoResponse().get(0).getName());
    }

    @Test
    void testFromDtoRequestToEntity(){

        OrderDetailsEntity expectedOrderDetailsEntity = OrderDetailsEntity.builder()
                .cost(750.0)
                .amount(2)
                .name("T-Shirt")
                .article(123456)
                .build();

        OrderEntity expectedOrderEntity = OrderEntity.builder()
                .recipient("Ivanov Ivan")
                .address("Moscow")
                .sum(1500.0)
                .delivery(Delivery.COURIER)
                .payment(Payment.CARD)
                .build();

        OrderDetailsCreateDtoRequest orderDetailsCreateDtoRequest = OrderDetailsCreateDtoRequest.builder()
                .cost(750.0)
                .amount(2)
                .name("T-Shirt")
                .article(123456)
                .build();


        OrderCreateDtoRequest orderCreateDtoRequest = OrderCreateDtoRequest.builder()
                .recipient("Ivanov Ivan")
                .address("Moscow")
                .delivery(Delivery.COURIER)
                .payment(Payment.CARD)
                .details(new ArrayList<>(List.of(orderDetailsCreateDtoRequest)))
                .build();

        when(orderDetailsMapper.fromDtoRequestToEntity(any(OrderDetailsCreateDtoRequest.class))).thenReturn(expectedOrderDetailsEntity);

        OrderEntity orderEntity = orderMapper.fromDtoRequestToEntity(orderCreateDtoRequest);

        assertEquals(expectedOrderEntity.getRecipient(), orderEntity.getRecipient());
        assertEquals(expectedOrderEntity.getAddress(), orderEntity.getAddress());
        assertEquals(expectedOrderEntity.getSum(), orderEntity.getSum());
        assertEquals(expectedOrderEntity.getDelivery(), orderEntity.getDelivery());
        assertEquals(expectedOrderEntity.getPayment(), orderEntity.getPayment());
        assertEquals(expectedOrderDetailsEntity.getArticle(), orderEntity.getOrderDetailEntities().get(0).getArticle());
        assertEquals(expectedOrderDetailsEntity.getAmount(), orderEntity.getOrderDetailEntities().get(0).getAmount());
        assertEquals(expectedOrderDetailsEntity.getCost(), orderEntity.getOrderDetailEntities().get(0).getCost());
        assertEquals(expectedOrderDetailsEntity.getName(), orderEntity.getOrderDetailEntities().get(0).getName());
    }
}
