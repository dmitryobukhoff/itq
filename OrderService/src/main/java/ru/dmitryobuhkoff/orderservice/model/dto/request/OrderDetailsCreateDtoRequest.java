package ru.dmitryobuhkoff.orderservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailsCreateDtoRequest {
    @JsonProperty("article")
    private Integer article;

    @JsonProperty("name")
    private String name;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("cost")
    private Double cost;
}
