package ru.dmitryobukhoff.orderservice;
import org.junit.jupiter.api.Test;
import ru.dmitryobuhkoff.orderservice.mapper.OrderDetailsMapper;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderDetailsCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDetailsDtoResponse;


import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailsMapperTest {
    private final OrderDetailsMapper orderDetailsMapper = new OrderDetailsMapper();

    @Test
    void testFromEntityToDtoResponse(){
        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .article(123456)
                .name("T-Shirt")
                .amount(2)
                .cost(1200.0)
                .build();

        OrderDetailsDtoResponse expectedResponse = OrderDetailsDtoResponse.builder()
                .article(123456)
                .name("T-Shirt")
                .amount(2)
                .cost(1200.0)
                .build();

        OrderDetailsDtoResponse response = orderDetailsMapper.fromEntityToDtoResponse(orderDetailsEntity);

        assertEquals(response.getArticle(), expectedResponse.getArticle());
        assertEquals(response.getName(), expectedResponse.getName());
        assertEquals(response.getAmount(), expectedResponse.getAmount());
        assertEquals(response.getCost(), expectedResponse.getCost());
    }

    @Test
    void testFromDtoRequestToEntity(){
        OrderDetailsCreateDtoRequest orderDetailsCreateDtoRequest = OrderDetailsCreateDtoRequest.builder()
                .name("T-Shirt")
                .article(123456)
                .amount(1)
                .cost(800.0)
                .build();

        OrderDetailsEntity expectedOrderDetailsEntity = OrderDetailsEntity.builder()
                .name("T-Shirt")
                .article(123456)
                .amount(1)
                .cost(800.0)
                .build();

        OrderDetailsEntity orderDetailsEntity = orderDetailsMapper.fromDtoRequestToEntity(orderDetailsCreateDtoRequest);

        assertEquals(orderDetailsEntity.getArticle(), expectedOrderDetailsEntity.getArticle());
        assertEquals(orderDetailsEntity.getName(), expectedOrderDetailsEntity.getName());
        assertEquals(orderDetailsEntity.getAmount(), expectedOrderDetailsEntity.getAmount());
        assertEquals(orderDetailsEntity.getCost(), expectedOrderDetailsEntity.getCost());
    }
}
