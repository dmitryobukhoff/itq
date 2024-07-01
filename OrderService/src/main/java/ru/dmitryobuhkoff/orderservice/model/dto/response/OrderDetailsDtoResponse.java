package ru.dmitryobuhkoff.orderservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderDetailsDtoResponse {

    @JsonProperty("article")
    private Integer article;

    @JsonProperty("name")
    private String name;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("cost")
    private Double cost;
}
