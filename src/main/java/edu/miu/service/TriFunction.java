package edu.miu.service;

/**
 * @author Rimon Mostafiz
 */
@FunctionalInterface
public interface TriFunction<V, X, Y, Z> {
    Z apply(V v, X x, Y y);
}
