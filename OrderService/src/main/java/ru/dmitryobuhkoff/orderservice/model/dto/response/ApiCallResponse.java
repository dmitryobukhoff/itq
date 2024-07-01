package ru.dmitryobuhkoff.orderservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiCallResponse implements Serializable {

    @JsonProperty("orderNumber")
    private String numberWithDate;

}
