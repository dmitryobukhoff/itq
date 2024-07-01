package ru.dmitryobuhkoff.orderservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderCreateDtoRequest {

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("address")
    private String address;

    @JsonProperty("payment")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Payment payment;

    @JsonProperty("delivery")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Delivery delivery;

    @JsonProperty("details")
    private List<OrderDetailsCreateDtoRequest> details;

}
