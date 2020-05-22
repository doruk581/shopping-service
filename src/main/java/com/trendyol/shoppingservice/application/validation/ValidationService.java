package com.trendyol.shoppingservice.application.validation;

public interface ValidationService<T> {

    ValidationResult validate(T request);
}
