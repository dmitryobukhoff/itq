package ru.dmitryobuhkoff.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmitryobuhkoff.orderservice.exceptions.EntityNotFoundException;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderFilterDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderWithoutProductDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDtoResponse;
import ru.dmitryobuhkoff.orderservice.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiURLs.ORDERS)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = ApiURLs.PATH_ID)
    public ResponseEntity<?> getOrderById(@PathVariable("id") UUID uuid)
        throws EntityNotFoundException {
        OrderEntity order = orderService.getOrderById(uuid);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredOrders(@RequestBody OrderFilterDtoRequest orderFilterDtoRequest)
        throws EntityNotFoundException {
        List<OrderDtoResponse> orders = orderService.getFilteredOrder(orderFilterDtoRequest);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @GetMapping("/order_without_article")
    public ResponseEntity<?> getOrdersWithoutProduct(@RequestBody OrderWithoutProductDtoRequest orderWithoutProductDtoRequest)
        throws EntityNotFoundException {
        List<OrderDtoResponse> orders = orderService.getOrdersWithoutProduct(orderWithoutProductDtoRequest);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateDtoRequest orderCreateDtoRequest){
        OrderDtoResponse orderDtoResponse = orderService.createOrder(orderCreateDtoRequest);
        return new ResponseEntity<>(orderDtoResponse, HttpStatus.CREATED);
    }


}
