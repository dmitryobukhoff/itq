package ru.dmitryobuhkoff.orderservice.repository.unifier;

public interface Unifier<T, K> {
    T unify(K list);
}
