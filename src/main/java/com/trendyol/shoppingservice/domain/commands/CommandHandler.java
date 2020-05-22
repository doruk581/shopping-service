package com.trendyol.shoppingservice.domain.commands;

public interface CommandHandler<T> {

    void handle(T t);
}