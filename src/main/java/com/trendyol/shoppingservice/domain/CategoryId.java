package com.trendyol.shoppingservice.domain;

import java.util.Arrays;

public enum CategoryId {

    MALE("ERKEK"),
    CLOTHING("GİYİM"),
    SHOE("AYAKKABI"),
    COSMETIC("KOZMETİK"),
    ELECTRONICS("ELEKTRONİK"),
    SUPERMARKET("SÜPERMARKET"),
    HOME_CARE_CLEANING("EV, BAKIM&TEMİZLİK"),
    PETSHOP("PETSHOP"),
    COMPUTER("BİLGİSAYAR"),
    NOTEBOOK("LAPTOP"),
    NONE(null);


    private final String title;


    CategoryId(String title) {
        this.title = title;
    }

    public static CategoryId of(String title) {
        return Arrays.stream(CategoryId.values())
                .filter(type -> type.title.equals(title))
                .findFirst()
                .orElse(NONE);
    }

    public String id() {
        return title;
    }

    public Boolean sameValueAs(CategoryId type) {
        return this.equals(type);
    }


}
