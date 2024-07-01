package ru.dmitryobuhkoff.orderservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderDtoResponse {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("number")
    private String number;

    @JsonProperty("sum")
    private Double sum;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("address")
    private String address;

    @JsonProperty("payment")
    private Payment payment;

    @JsonProperty("delivery")
    private Delivery delivery;

    @JsonProperty("details")
    private List<OrderDetailsDtoResponse> orderDetailsDtoResponse;
}
