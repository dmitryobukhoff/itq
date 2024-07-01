package ru.dmitryobukhoff.numbergenerator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.dmitryobukhoff.numbergenerator.exception.NotUniqueOrderNumberException;
import ru.dmitryobukhoff.numbergenerator.mapper.NumberMapper;
import ru.dmitryobukhoff.numbergenerator.repository.NumberRepository;
import ru.dmitryobukhoff.numbergenerator.service.impl.NumberGenerateServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NumberGenerateServiceImplTest {

    @Mock
    private NumberRepository numberRepository;

    @Mock
    private NumberMapper numberMapper;

    @InjectMocks
    private NumberGenerateServiceImpl numberGenerateService;

    @Test
    void testGenerateNumberNotUniq(){
        UUID uuid = UUID.randomUUID();

        when(numberRepository.isOrderNumberUnique(any(String.class))).thenReturn(false);

        assertThrows(NotUniqueOrderNumberException.class, () -> numberGenerateService.generateNumber(uuid));
    }
}