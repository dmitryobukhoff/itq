package ru.dmitryobuhkoff.orderservice.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderFindDtoResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OrderRowMapper implements RowMapper<OrderFindDtoResponse> {
    @Override
    public OrderFindDtoResponse mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .number(resultSet.getString("order_number"))
                .sum(resultSet.getDouble("order_sum"))
                .date(resultSet.getDate("order_date"))
                .recipient(resultSet.getString("order_recipient"))
                .address(resultSet.getString("order_address"))
                .payment(Payment.valueOf(resultSet.getString("order_payment")))
                .delivery(Delivery.valueOf(resultSet.getString("order_delivery")))
                .build();

        OrderDetailsEntity orderDetailsEntity = OrderDetailsEntity.builder()
                .article(resultSet.getInt("product_article"))
                .name(resultSet.getString("product_name"))
                .amount(resultSet.getInt("product_amount"))
                .cost(resultSet.getDouble("product_cost"))
                .build();

        return new OrderFindDtoResponse(orderEntity, orderDetailsEntity);

    }
}
