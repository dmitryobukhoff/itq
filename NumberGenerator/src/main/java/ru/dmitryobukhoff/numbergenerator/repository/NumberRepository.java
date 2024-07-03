package ru.dmitryobukhoff.numbergenerator.repository;

import io.lettuce.core.RedisCommandExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import ru.dmitryobukhoff.numbergenerator.exception.DbAccessException;

import java.util.Arrays;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NumberRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public void addOrderNumber(String orderNumber, UUID uuid){
        try{
            log.info("OrderNumber: {}, UUID: {}", orderNumber, uuid);
            redisTemplate.opsForValue().set(orderNumber, uuid.toString());
        }catch (RedisCommandExecutionException | DataAccessException exception){
            log.error("Redis error in method 'addOrderNumber(String orderNumber, UUID uuid)' of NumberRepository.class");
            log.error(exception.getMessage());
            throw new DbAccessException("Redis error while adding order");
        }
    }

    public Boolean isOrderNumberUnique(String orderNumber){
        try{
            return redisTemplate.hasKey(orderNumber);
        }catch (RedisCommandExecutionException | DataAccessException exception){
            log.error("Redis error in method 'isOrderNumberUnique(String orderNumber)' of NumberRepository.class");
            throw new DbAccessException("Redis error while check if number exist");
        }
    }
}
