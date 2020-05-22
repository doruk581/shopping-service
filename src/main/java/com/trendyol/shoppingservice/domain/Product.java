package com.trendyol.shoppingservice.domain;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;

    private String title;

    private BigDecimal price;

    private Category category;

    @BsonIgnore
    private Campaign appliedCampaign;


}
