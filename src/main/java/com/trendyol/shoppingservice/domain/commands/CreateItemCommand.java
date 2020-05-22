package com.trendyol.shoppingservice.domain.commands;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemCommand implements Command {

    private String commandId;
    private String id;
    private Integer quantity;
    private String couponId;

}
