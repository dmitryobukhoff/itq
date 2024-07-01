package ru.dmitryobukhoff.numbergenerator.mapper;

import org.springframework.stereotype.Component;
import ru.dmitryobukhoff.numbergenerator.model.dto.response.NumberResponse;

@Component
public class NumberMapper {
    public NumberResponse fromStringToNumberResponse(String orderNumber){
        return NumberResponse.builder()
                .number(orderNumber)
                .build();
    }
}
