package com.trendyol.shoppingservice.application;

import com.trendyol.shoppingservice.application.validation.ValidationResult;
import com.trendyol.shoppingservice.application.validation.ValidationService;
import com.trendyol.shoppingservice.domain.ProductNotExistException;
import com.trendyol.shoppingservice.domain.ShoppingService;
import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import com.trendyol.shoppingservice.interfaces.ErrorCode;
import com.trendyol.shoppingservice.interfaces.ServiceError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@Slf4j
public class ShoppingServiceController {

    private final ShoppingService shoppingService;

    private final ValidationService<CreateItemCommand> createItemCommandValidationService;

    public ShoppingServiceController(ShoppingService shoppingService, ValidationService<CreateItemCommand> createItemCommandValidationService) {
        this.shoppingService = shoppingService;
        this.createItemCommandValidationService = createItemCommandValidationService;
    }


    @PostMapping(path = "/shopping/addItem")
    public ResponseEntity addItem(@RequestBody CreateItemCommand command) {
        final ValidationResult validationResult = createItemCommandValidationService.validate(command);
        if (!validationResult.getIsValid()) {
            log.warn("Request was not valid!");
            return new ResponseEntity<>(ServiceError.create(HttpStatus.BAD_REQUEST.value(), validationResult.getMessage(), validationResult.getErrorCode().code()), HttpStatus.BAD_REQUEST);
        }
        shoppingService.create(command);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ServiceError> handleInternalServerError(Exception ex, WebRequest request) {
        log.error("Unhandled exception occurred!", ex);
        return new ResponseEntity<>(ServiceError.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotExistException.class)
    public final ResponseEntity<ServiceError> handleProductNotExistException(Exception ex, WebRequest request) {
        log.error("ProductNotExistException occurred!", ex);
        return new ResponseEntity<>(ServiceError.create(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ErrorCode.PRODUCTNOTEXISTINSYSTEM.code()), HttpStatus.BAD_REQUEST);
    }
}
