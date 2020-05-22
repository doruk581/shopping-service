package com.trendyol.shoppingservice.interfaces;

public enum ErrorCode {

    CHARTIDNOTACCAPTABLE("SS1001"),
    PRODUCTIDNOTACCEPTABLE("SS1002"),
    PRODUCTQUANTITYNOTACCEPTABLE("SS1003"),
    PRODUCTNOTEXISTINSYSTEM("SS1004"),
    CARTNOTEXIST("SS1005");


    private String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}