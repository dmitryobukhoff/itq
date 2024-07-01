package ru.dmitryobuhkoff.orderservice.mapper;

import org.springframework.stereotype.Component;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.request.OrderDetailsCreateDtoRequest;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderDetailsDtoResponse;

@Component
public class OrderDetailsMapper {

    public OrderDetailsDtoResponse fromEntityToDtoResponse(OrderDetailsEntity orderDetailsEntity){
        return OrderDetailsDtoResponse.builder()
                .article(orderDetailsEntity.getArticle())
                .name(orderDetailsEntity.getName())
                .amount(orderDetailsEntity.getAmount())
                .cost(orderDetailsEntity.getCost())
                .build();
    }

    public OrderDetailsEntity fromDtoRequestToEntity(OrderDetailsCreateDtoRequest orderDetailsCreateDtoRequest){
        return OrderDetailsEntity.builder()
                .article(orderDetailsCreateDtoRequest.getArticle())
                .amount(orderDetailsCreateDtoRequest.getAmount())
                .name(orderDetailsCreateDtoRequest.getName())
                .cost(orderDetailsCreateDtoRequest.getCost())
                .build();
    }
}
