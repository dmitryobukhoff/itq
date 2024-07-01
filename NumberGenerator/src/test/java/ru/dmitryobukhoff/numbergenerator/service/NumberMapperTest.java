package ru.dmitryobukhoff.numbergenerator.service;

import org.junit.jupiter.api.Test;
import ru.dmitryobukhoff.numbergenerator.mapper.NumberMapper;
import ru.dmitryobukhoff.numbergenerator.model.dto.response.NumberResponse;

import static org.junit.jupiter.api.Assertions.*;

public class NumberMapperTest {

    private final NumberMapper numberMapper = new NumberMapper();

    @Test
    void testFromStringToNumberResponse(){
        String number = "aaaaa20240701";

        NumberResponse expectNumber = new NumberResponse("aaaaa20240701");

        NumberResponse numberResponse = numberMapper.fromStringToNumberResponse(number);

        assertEquals(expectNumber.getNumber(), numberResponse.getNumber());
    }
}
