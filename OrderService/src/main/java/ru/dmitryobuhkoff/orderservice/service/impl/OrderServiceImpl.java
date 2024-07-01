package ru.dmitryobuhkoff.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dmitryobuhkoff.orderservice.exceptions.*;
import ru.dmitryobuhkoff.orderservice.mapper.OrderMapper;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderFilterDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderWithoutProductDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.ApiCallResponse;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderDetailsCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDtoResponse;
import ru.dmitryobuhkoff.orderservice.repository.OrderRepository;
import ru.dmitryobuhkoff.orderservice.service.ApiCallService;
import ru.dmitryobuhkoff.orderservice.service.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ApiCallService apiCallService;

    @Override
    public OrderEntity getOrderById(UUID uuid) throws DBAccessException{
        return orderRepository.findOrderById(uuid)
                .orElseThrow(() -> {
                    log.error("Order with uuid {} not found", uuid);
                    return new EntityNotFoundException("Order with uuid " + uuid + " not found");
                });
    }
    @Override
    public List<OrderDtoResponse> getFilteredOrder(OrderFilterDtoRequest orderFilterDtoRequest) throws DBAccessException{
        List<OrderEntity> orders = orderRepository.getOrderByDateAndSum(orderFilterDtoRequest.getSum(), orderFilterDtoRequest.getDate());

        if(orders.isEmpty()) {
            log.error("No orders found matching filter criteria: sum = {}, date = {}", orderFilterDtoRequest.getSum(), orderFilterDtoRequest.getDate());
            throw new EntityNotFoundException("No orders found matching filter criteria");
        }
        return orders.stream()
                .map(orderMapper::fromEntityToDtoResponse)
                .toList();
    }
    @Override
    public List<OrderDtoResponse> getOrdersWithoutProduct(OrderWithoutProductDtoRequest orderWithoutProductDtoRequest) throws DBAccessException{
        List<OrderEntity> orders = orderRepository.getOrdersWithoutProduct(orderWithoutProductDtoRequest.getArticle(),
                orderWithoutProductDtoRequest.getDateSince(), orderWithoutProductDtoRequest.getDateBefore());

        if(orders.isEmpty()) {
            log.error("No orders found no exist with product a article '{}'", orderWithoutProductDtoRequest.getArticle());
            throw new EntityNotFoundException("No orders found no exist with product a article '" + orderWithoutProductDtoRequest.getArticle() + "'");
        }
        return orders.stream()
                .map(orderMapper::fromEntityToDtoResponse)
                .toList();
    }
    @Override
    public OrderDtoResponse createOrder(OrderCreateDtoRequest orderCreateDtoRequest) throws DBAccessException{
        if(orderCreateDtoRequest.getDetails().isEmpty()) {
            log.error("Details can't be empty while creating new order");
            throw new ApiCallException("Details can't be empty");
        }
        UUID uuid = UUID.randomUUID();
        ApiCallResponse apiCallResponse = apiCallService.requestForNumber(uuid);
        OrderEntity order = unifyOrder(orderCreateDtoRequest, apiCallResponse, uuid);
        return orderMapper.fromEntityToDtoResponse(orderRepository.createOrder(order));
    }

    private OrderEntity unifyOrder(OrderCreateDtoRequest orderCreateDtoRequest, ApiCallResponse apiCallResponse, UUID uuid)  {
        String numberWithDate = apiCallResponse.getNumberWithDate();
        String orderNumber = numberWithDate.substring(0, 5);
        SimpleDateFormat pattern = new SimpleDateFormat("yyyyMMdd");
        OrderEntity order = orderMapper.fromDtoRequestToEntity(orderCreateDtoRequest);
        order.setId(uuid);
        order.setNumber(orderNumber);
        try {
            Date date = pattern.parse(numberWithDate.substring(5));
            order.setDate(date);
            return order;
        } catch (ParseException e) {
            log.error("Incorrect date {}", numberWithDate.substring(5));
            throw new DateParseException("Incorrect date " + numberWithDate.substring(5));
        }
    }

}
