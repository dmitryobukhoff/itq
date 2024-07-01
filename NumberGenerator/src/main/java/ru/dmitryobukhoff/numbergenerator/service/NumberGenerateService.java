package ru.dmitryobukhoff.numbergenerator.service;

import ru.dmitryobukhoff.numbergenerator.model.dto.response.NumberResponse;

import java.util.UUID;

public interface NumberGenerateService {
    NumberResponse generateNumber(UUID uuid);
}
