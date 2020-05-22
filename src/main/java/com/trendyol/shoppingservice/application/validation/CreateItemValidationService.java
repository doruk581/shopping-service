package com.trendyol.shoppingservice.application.validation;

import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import com.trendyol.shoppingservice.interfaces.ErrorCode;

import java.util.function.Predicate;

public class CreateItemValidationService implements ValidationService<CreateItemCommand> {

    private final Predicate<String> isNullOrBlank = data -> data == null || data.trim().isEmpty();

    @Override
    public ValidationResult validate(final CreateItemCommand request) {

        if (isNullOrBlank.test(request.getCommandId()))
            return ValidationResult.error("Chart Id is not acceptable.", ErrorCode.CHARTIDNOTACCAPTABLE);

        if (isNullOrBlank.test(request.getId()))
            return ValidationResult.error("Product Id is not acceptable", ErrorCode.PRODUCTIDNOTACCEPTABLE);

        if (request.getQuantity() == null || request.getQuantity() <= 0)
            return ValidationResult.error("Product Quantity is not acceptable", ErrorCode.PRODUCTQUANTITYNOTACCEPTABLE);

        return ValidationResult.success();
    }
}
