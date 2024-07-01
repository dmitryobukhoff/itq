package ru.dmitryobuhkoff.orderservice.service;

import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderFilterDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderWithoutProductDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDtoResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderEntity getOrderById(UUID uuid);
    List<OrderDtoResponse> getFilteredOrder(OrderFilterDtoRequest orderFilterDtoRequest);
    List<OrderDtoResponse> getOrdersWithoutProduct(OrderWithoutProductDtoRequest orderWithoutProductDtoRequest);
    OrderDtoResponse createOrder(OrderCreateDtoRequest orderCreateDtoRequest);
}
