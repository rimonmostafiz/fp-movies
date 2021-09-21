package edu.miu.service;

public interface QuadFunction<X, Y, Z, T, V> {
    V apply(X x, Y y, Z z, T t);
}
