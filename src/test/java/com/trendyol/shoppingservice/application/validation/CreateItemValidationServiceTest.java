package com.trendyol.shoppingservice.application.validation;

import com.trendyol.shoppingservice.domain.commands.CreateItemCommand;
import com.trendyol.shoppingservice.interfaces.ErrorCode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateItemValidationServiceTest {

    private CreateItemValidationService createItemValidationService;

    @Before
    public void initiate() {
        createItemValidationService = new CreateItemValidationService();
    }


    @Test
    public void controlThatValidateMethodShouldReturnFalseWhenCommandIdIsNull() {
        final CreateItemCommand command = new CreateItemCommand();

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.FALSE)));
        assertThat(validationResult.getErrorCode(), is(equalTo(ErrorCode.CHARTIDNOTACCAPTABLE)));
    }

    @Test
    public void controlThatValidateMethodShouldReturnFalseWhenCommandIdIsEmpty() {
        final CreateItemCommand command = new CreateItemCommand();
        command.setCommandId("");

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.FALSE)));
        assertThat(validationResult.getErrorCode(), is(equalTo(ErrorCode.CHARTIDNOTACCAPTABLE)));
    }

    @Test
    public void controlThatValidateMethodShouldReturnFalseWhenProductIdIsNull() {
        final CreateItemCommand command = new CreateItemCommand();
        command.setCommandId("111");

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.FALSE)));
        assertThat(validationResult.getErrorCode(), is(equalTo(ErrorCode.PRODUCTIDNOTACCEPTABLE)));
    }

    @Test
    public void controlThatValidateMethodShouldReturnFalseWhenProductIdIsEmpty() {
        final CreateItemCommand command = new CreateItemCommand();
        command.setCommandId("111");
        command.setId("");

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.FALSE)));
        assertThat(validationResult.getErrorCode(), is(equalTo(ErrorCode.PRODUCTIDNOTACCEPTABLE)));
    }

    @Test
    public void controlThatValidateMethodShouldReturnFalseWhenQuantityIsNull() {
        final CreateItemCommand command = new CreateItemCommand();
        command.setCommandId("111");
        command.setId("111");

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.FALSE)));
        assertThat(validationResult.getErrorCode(), is(equalTo(ErrorCode.PRODUCTQUANTITYNOTACCEPTABLE)));
    }

    @Test
    public void controlThatValidateMethodShouldReturnFalseWhenQuantityIsZero() {
        final CreateItemCommand command = new CreateItemCommand();
        command.setCommandId("111");
        command.setId("111");
        command.setQuantity(0);

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.FALSE)));
        assertThat(validationResult.getErrorCode(), is(equalTo(ErrorCode.PRODUCTQUANTITYNOTACCEPTABLE)));
    }

    @Test
    public void controlThatValidateMethodShouldReturnFalseWhenQuantityIsBelowZero() {
        final CreateItemCommand command = new CreateItemCommand();
        command.setCommandId("111");
        command.setId("111");
        command.setQuantity(-1);

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.FALSE)));
        assertThat(validationResult.getErrorCode(), is(equalTo(ErrorCode.PRODUCTQUANTITYNOTACCEPTABLE)));
    }

    @Test
    public void controlThatValidateMethodShouldSuccessWhenAllInputsOK() {
        final CreateItemCommand command = new CreateItemCommand();
        command.setCommandId("111");
        command.setId("111");
        command.setQuantity(5);

        final ValidationResult validationResult = createItemValidationService.validate(command);

        assertThat(validationResult.getIsValid(), is(equalTo(Boolean.TRUE)));
    }


}