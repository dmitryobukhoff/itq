package ru.dmitryobuhkoff.orderservice.model;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailsEntity {
    private Integer article;
    private String name;
    private Integer amount;
    private Double cost;

    @Override
    public String toString() {
        return "OrderDetails{" +
                "article=" + article +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsEntity that = (OrderDetailsEntity) o;
        return article.equals(that.article) && name.equals(that.name) && amount.equals(that.amount) && cost.equals(that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, name, amount, cost);
    }
}
