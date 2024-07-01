package ru.dmitryobuhkoff.orderservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dmitryobuhkoff.orderservice.exceptions.DBAccessException;
import ru.dmitryobuhkoff.orderservice.model.OrderEntity;
import ru.dmitryobuhkoff.orderservice.model.OrderDetailsEntity;
import ru.dmitryobuhkoff.orderservice.model.dto.response.OrderFindDtoResponse;
import ru.dmitryobuhkoff.orderservice.repository.mapper.OrderRowMapper;
import ru.dmitryobuhkoff.orderservice.repository.unifier.OrderListUnifier;
import ru.dmitryobuhkoff.orderservice.repository.unifier.OrderUnifier;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final OrderUnifier orderUnifier;
    private final OrderListUnifier orderListUnifier;

    public Optional<OrderEntity> findOrderById(UUID uuid){
        try{
            String url = "SELECT * FROM order_service.get_order_with_details_by_id(?);";
            List<OrderFindDtoResponse> orderWithDetails = jdbcTemplate.query(url, new OrderRowMapper(), uuid);
            if(orderWithDetails.isEmpty())
                return Optional.empty();
            else{
                return Optional.of(orderUnifier.unify(orderWithDetails));
            }
        }catch (DataAccessException exception){
            log.error("DataAccessException in OrderService while using method findOrderById(UUID uuid)");
            throw new DBAccessException("Error while finding order by id");
        }

    }

    public List<OrderEntity> getOrderByDateAndSum(Double sum, Date date){
        try{
            String url = "SELECT * FROM order_service.get_order_by_date_and_sum(?,?)";
            List<OrderFindDtoResponse> orders = jdbcTemplate.query(url, new OrderRowMapper(), date, sum);
            return orderListUnifier.unify(orders);
        }catch (DataAccessException exception){
            log.error("DataAccessException in OrderService while using method getOrderByDateAndSum(Double sum, Date date)");
            throw new DBAccessException("Error while getting data by date and sum");
        }

    }

    public List<OrderEntity> getOrdersWithoutProduct(Integer article, Date dateSince, Date dateBefore){
        try{
            String url = "SELECT * FROM order_service.filter_order_by_article_and_date(?,?,?)";
            List<OrderFindDtoResponse> filteredOrders = jdbcTemplate.query(url, new OrderRowMapper(), article, dateSince, dateBefore);
            return orderListUnifier.unify(filteredOrders);
        }catch (DataAccessException exception){
            log.error("DataAccessException in OrderService while using method getOrdersWithoutProduct(Integer article, Date dateSince, Date dateBefore)");
            throw new DBAccessException("Error while getting orders without product");
        }

    }

    @Transactional
    public OrderEntity createOrder(OrderEntity order){
        try{

            String urlForOrder = "INSERT INTO order_service.orders(id, order_number, order_sum, order_date, order_recipient, order_address," +
                    "                                               order_payment, order_delivery)" +
                    "                  VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            String urlForProduct = "INSERT INTO order_service.order_details(product_article, product_name, product_amount, product_cost, order_id)" +
                    "    VALUES (?, ?, ?, ?, ?);";

            jdbcTemplate.update(urlForOrder, order.getId(), order.getNumber(), order.getSum(), order.getDate(), order.getRecipient(), order.getAddress(),
                    String.valueOf(order.getPayment()), String.valueOf(order.getDelivery()));

            List<OrderDetailsEntity> orderDetailEntities = order.getOrderDetailEntities();

            orderDetailEntities.forEach(orderDetail -> {
                jdbcTemplate.update(urlForProduct, orderDetail.getArticle(), orderDetail.getName(), orderDetail.getAmount(), orderDetail.getCost(),
                        order.getId());
            });

            return order;
        }catch (DataAccessException exception){
            log.error("DataAccessException in OrderService while using method createOrder(OrderEntity order)");
            throw new DBAccessException("Error while creating order");
        }

    }



}
