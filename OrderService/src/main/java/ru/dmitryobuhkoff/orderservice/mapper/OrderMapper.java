package ru.dmitryobuhkoff.orderservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDtoResponse;

@Component
@RequiredArgsConstructor
public class OrderMapper{

    private final OrderDetailsMapper orderDetailsMapper;

    public OrderDtoResponse fromEntityToDtoResponse(OrderEntity order){
        return OrderDtoResponse.builder()
                .id(order.getId())
                .number(order.getNumber())
                .sum(order.getSum())
                .date(order.getDate())
                .recipient(order.getRecipient())
                .address(order.getAddress())
                .payment(order.getPayment())
                .delivery(order.getDelivery())
                .orderDetailsDtoResponse(order.getOrderDetailEntities().stream()
                        .map(orderDetailsMapper::fromEntityToDtoResponse)
                        .toList())
                .build();
    }

    public OrderEntity fromDtoRequestToEntity(OrderCreateDtoRequest orderCreateDtoRequest){
        return OrderEntity.builder()
                .recipient(orderCreateDtoRequest.getRecipient())
                .address(orderCreateDtoRequest.getAddress())
                .delivery(orderCreateDtoRequest.getDelivery())
                .payment(orderCreateDtoRequest.getPayment())
                .orderDetailEntities(orderCreateDtoRequest.getDetails().stream().map(orderDetailsMapper::fromDtoRequestToEntity).toList())
                .sum(orderCreateDtoRequest.getDetails().stream().mapToDouble(orderDetail -> orderDetail.getCost() * orderDetail.getAmount()).sum())
                .build();
    }
}
