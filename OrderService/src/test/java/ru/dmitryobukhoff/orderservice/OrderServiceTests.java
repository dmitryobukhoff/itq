package ru.dmitryobukhoff.orderservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dmitryobuhkoff.orderservice.exceptions.ApiCallException;
import ru.dmitryobuhkoff.orderservice.exceptions.EntityNotFoundException;
import ru.dmitryobuhkoff.orderservice.mapper.OrderMapper;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderDetailsCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderFilterDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderWithoutProductDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.ApiCallResponse;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDtoResponse;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;
import ru.dmitryobuhkoff.orderservice.repository.OrderRepository;
import ru.dmitryobuhkoff.orderservice.service.ApiCallService;
import ru.dmitryobuhkoff.orderservice.service.impl.OrderServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ApiCallService apiCallService;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void testGetOrderByIdFound(){
        UUID uuid = UUID.randomUUID();
        OrderEntity expectedOrderEntity = new OrderEntity();
        when(orderRepository.findOrderById(uuid)).thenReturn(Optional.of(expectedOrderEntity));

        OrderEntity result = orderService.getOrderById(uuid);

        assertNotNull(result);
        assertEquals(expectedOrderEntity, result);
        verify(orderRepository, times(1)).findOrderById(uuid);
    }

    @Test
    void testGetOrderByIdNotFound(){
        UUID uuid = UUID.randomUUID();
        when(orderRepository.findOrderById(uuid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderById(uuid));

        verify(orderRepository, times(1)).findOrderById(uuid);
    }

    @Test
    void testGetFilteredOrderFound(){
        OrderFilterDtoRequest request = new OrderFilterDtoRequest(100.0, new Date());

        OrderEntity expectedOrder = new OrderEntity();
        when(orderRepository.getOrderByDateAndSum(any(Double.class), any(Date.class))).thenReturn(List.of(expectedOrder));

        List<OrderDtoResponse> response = orderService.getFilteredOrder(request);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(orderRepository, times(1)).getOrderByDateAndSum(any(Double.class), any(Date.class));
        verify(orderMapper, times(1)).fromEntityToDtoResponse(any(OrderEntity.class));
    }

    @Test
    void testGetFilteredOrderNotFound(){
        OrderFilterDtoRequest request = new OrderFilterDtoRequest(100.0, new Date());

        when(orderRepository.getOrderByDateAndSum(any(Double.class), any(Date.class))).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> orderService.getFilteredOrder(request));


        verify(orderRepository, times(1)).getOrderByDateAndSum(any(Double.class), any(Date.class));
        verify(orderMapper, times(0)).fromEntityToDtoResponse(any(OrderEntity.class));
    }

    @Test
    void testGetOrdersWithoutProductFound(){
        OrderWithoutProductDtoRequest request = new OrderWithoutProductDtoRequest(123456, new Date(), new Date());

        OrderEntity expectOrder = new OrderEntity();
        when(orderRepository.getOrdersWithoutProduct(request.getArticle(), request.getDateSince(), request.getDateBefore()))
                .thenReturn(List.of(expectOrder));

        List<OrderDtoResponse> response = orderService.getOrdersWithoutProduct(request);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(orderRepository, times(1)).getOrdersWithoutProduct(request.getArticle(),
                request.getDateSince(), request.getDateBefore());
        verify(orderMapper, times(1)).fromEntityToDtoResponse(expectOrder);
    }

    @Test
    void testGetOrdersWithoutProductNotFound(){
        OrderWithoutProductDtoRequest request = new OrderWithoutProductDtoRequest(123456, new Date(), new Date());

        when(orderRepository.getOrdersWithoutProduct(request.getArticle(), request.getDateSince(), request.getDateBefore()))
                .thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrdersWithoutProduct(request));

        verify(orderRepository, times(1)).getOrdersWithoutProduct(request.getArticle(),
                request.getDateSince(), request.getDateBefore());
        verify(orderMapper, times(0)).fromEntityToDtoResponse(any(OrderEntity.class));
    }
    @Test
    void testCreateOrderWithDetails(){
        OrderCreateDtoRequest request = new OrderCreateDtoRequest(
                "Ivanov Ivan",
                "Moscow",
                Payment.CASH,
                Delivery.COURIER,
                new ArrayList<>()
        );
        request.setDetails(List.of(new OrderDetailsCreateDtoRequest(
               123456,
               "Boots",
                1,
                2000.0
        )));

        OrderDtoResponse orderDtoResponse = OrderDtoResponse.builder()
                .recipient(request.getRecipient())
                .address(request.getAddress())
                .delivery(request.getDelivery())
                .payment(request.getPayment())
                .number("abcde")
                .sum(2000.0)
                .build();

        ApiCallResponse apiCallResponse = new ApiCallResponse("abcde20240701");
        OrderEntity order = new OrderEntity();

        when(orderRepository.createOrder(any(OrderEntity.class))).thenReturn(order);
        when(apiCallService.requestForNumber(any(UUID.class))).thenReturn(apiCallResponse);
        when(orderMapper.fromDtoRequestToEntity(any(OrderCreateDtoRequest.class))).thenReturn(order);
        when(orderMapper.fromEntityToDtoResponse(any(OrderEntity.class))).thenReturn(orderDtoResponse);

        OrderDtoResponse response = orderService.createOrder(request);

        assertEquals(response.getRecipient(), "Ivanov Ivan");
        assertEquals(response.getNumber(), "abcde");
        assertEquals(response.getSum(), 2000.0);
        assertEquals(response.getAddress(), request.getAddress());
        assertEquals(response.getPayment(), request.getPayment());
        assertEquals(response.getDelivery(), request.getDelivery());
        verify(orderMapper, times(1)).fromDtoRequestToEntity(any(OrderCreateDtoRequest.class));
        verify(orderRepository, times(1)).createOrder(any(OrderEntity.class));
    }

    @Test
    void testCreateOrderWithoutDetails(){
        OrderCreateDtoRequest request = new OrderCreateDtoRequest(
                "Ivanov Ivan",
                "Moscow",
                Payment.CASH,
                Delivery.COURIER,
                new ArrayList<>()
        );

        assertThrows(ApiCallException.class, () -> orderService.createOrder(request));

        verify(orderMapper, times(0)).fromDtoRequestToEntity(any(OrderCreateDtoRequest.class));
        verify(orderRepository, times(0)).createOrder(any(OrderEntity.class));
    }
}
