package ru.dmitryobukhoff.numbergenerator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmitryobukhoff.numbergenerator.exception.DbAccessException;
import ru.dmitryobukhoff.numbergenerator.exception.NotUniqueOrderNumberException;
import ru.dmitryobukhoff.numbergenerator.exception.SHA256AlgorithmException;
import ru.dmitryobukhoff.numbergenerator.mapper.NumberMapper;
import ru.dmitryobukhoff.numbergenerator.model.dto.response.NumberResponse;
import ru.dmitryobukhoff.numbergenerator.repository.NumberRepository;
import ru.dmitryobukhoff.numbergenerator.util.NumberGenerator;
import ru.dmitryobukhoff.numbergenerator.service.NumberGenerateService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NumberGenerateServiceImpl implements NumberGenerateService {

    private final NumberRepository numberRepository;
    private final NumberMapper numberMapper;
    @Override
    public NumberResponse generateNumber(UUID uuid) throws SHA256AlgorithmException, NotUniqueOrderNumberException, DbAccessException {
        String orderNumber = NumberGenerator.generateNumber(uuid);
        numberRepository.addOrderNumber(orderNumber, uuid);
        this.checkIsNumberUniq(orderNumber);
        orderNumber = orderNumber.concat(this.getTime());
        return numberMapper.fromStringToNumberResponse(orderNumber);
    }

    private boolean checkIsNumberUniq(String orderNumber){
        if(!numberRepository.isOrderNumberUnique(orderNumber))
            throw new NotUniqueOrderNumberException("Number '" + orderNumber + "' is not uniq!");
        return true;
    }

    private String getTime(){
        LocalDateTime date = LocalDateTime.now();
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonth().getValue());
        month = month.length() == 1 ? "0".concat(month) : month;
        String day = String.valueOf(date.getDayOfMonth());
        day = day.length() == 1 ? "0".concat(day) : day;
        return year.concat(month).concat(day);
    }
}
