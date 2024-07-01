package ru.dmitryobuhkoff.orderservice.model;

import lombok.*;
import ru.dmitryobuhkoff.orderservice.model.enums.Delivery;
import ru.dmitryobuhkoff.orderservice.model.enums.Payment;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderEntity{
    private UUID id;
    private String number;
    private Double sum;
    private Date date;
    private String recipient;
    private String address;
    private Payment payment;
    private Delivery delivery;

    private List<OrderDetailsEntity> orderDetailEntities;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", sum=" + sum +
                ", date=" + date +
                ", recipient='" + recipient + '\'' +
                ", address='" + address + '\'' +
                ", payment=" + payment +
                ", delivery=" + delivery +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id.equals(that.id) && number.equals(that.number) && sum.equals(that.sum) && date.equals(that.date) && recipient.equals(that.recipient) && address.equals(that.address) && payment == that.payment && delivery == that.delivery;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, sum, date, recipient, address, payment, delivery);
    }
}
