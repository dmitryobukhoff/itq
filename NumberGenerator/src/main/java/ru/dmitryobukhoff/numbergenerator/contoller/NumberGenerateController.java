package ru.dmitryobukhoff.numbergenerator.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmitryobukhoff.numbergenerator.model.dto.response.NumberResponse;
import ru.dmitryobukhoff.numbergenerator.service.NumberGenerateService;

import java.util.UUID;

@RestController
@RequestMapping(ApiURLs.GENERATING)
@RequiredArgsConstructor
public class NumberGenerateController {
    private final NumberGenerateService numberGenerateService;

    @GetMapping(path = "/generate")
    public ResponseEntity<?> getGeneratedNumber(@RequestParam("uuid") UUID uuid){
        NumberResponse numberResponse = numberGenerateService.generateNumber(uuid);
        return new ResponseEntity<>(numberResponse, HttpStatus.OK);
    }


}
