package ru.dmitryobukhoff.numbergenerator.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class NumberResponse {
    @JsonProperty("orderNumber")
    private String number;
}
