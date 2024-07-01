package ru.dmitryobukhoff.numbergenerator.service;

import org.junit.jupiter.api.Test;
import ru.dmitryobukhoff.numbergenerator.util.NumberGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class NumberGeneratorTest {

    private static final int LENGTH = 5;
    private static final int TIMES = 10_000;

    /**
     *  Тест проверяте, что при генерации кода TIMES раз, длина будет сохранятся
     */
    @Test
    void checkLength(){
        boolean isLengthEquals = true;
        for(int i = 0; i < TIMES; i++){
            UUID uuid = UUID.randomUUID();
            String orderNumber = NumberGenerator.generateNumber(uuid);
            if(orderNumber.length() != LENGTH) isLengthEquals = false;
        }
        assertTrue(isLengthEquals);
    }

    /**
     *  Тест проверяет процент уникальности сгенерированных кодов
     *
     *  Преимущества:
     *      - Для генерации кода не происходит запрос в Redis
     *      - Используется алгоритма хеширования SHA-256, что обеспечивает уникальность и безопасность результата
     *      - Используется UUID, который представляет собой 128-битное число
     *
     *  Недостатки:
     *      - Время может повторяться, если интервалы близки друг к другу
     *      - Если входные данные близки друг к другу, то вероятность коллизии увелиичтся
     *      - Берется подстрока из первых 5-ти символов что делает вероятность коллизии еще больше
     *
     *   Шансы коллизии:
     *      - TIMES = 1000 ( 0.1 ... 0.3 ) %
     *      - TIMES = 10_000 ( 0.5 ... 0.61 ) %
     *      - TIMES = 50_000 ( 2.34 ... 2.45 ) %
     *      - TIMES = 100_000 ( 4.5 ... 4.65 ) %
     *      - TIMES = 1_000_000 ( 35 ... 36 ) %
     */
    @Test
    void checkOnUniq(){
        Set<String> numbers = new HashSet<>();

        int amountOfNotUniq = 0;

        for(int i = 0; i < TIMES; i++){
            UUID uuid = UUID.randomUUID();
            String orderNumber = NumberGenerator.generateNumber(uuid);
            if(numbers.contains(orderNumber)){
                amountOfNotUniq++;
            }
            numbers.add(orderNumber);

        }
        System.out.println("Percentage of matches: " + Double.toString(amountOfNotUniq*1.0/TIMES * 100.0) + " %");
    }


}
