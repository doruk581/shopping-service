package com.trendyol.shoppingservice.domain.flow;

public interface Handler<T> {

    void setSuccessor(Handler<T> handler);

    void handle(T t);
}