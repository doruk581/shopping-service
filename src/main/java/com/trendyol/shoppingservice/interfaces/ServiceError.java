package com.trendyol.shoppingservice.interfaces;

public class ServiceError {

    private Error error;

    private ServiceError(int status, String message, String errorCode) {
        this.error = new Error(status, message, errorCode);
    }

    public ServiceError(Error error) {
        this.error = error;
    }

    public static ServiceError create(int status, String message, String errorCode) {
        return new ServiceError(status, message, errorCode);
    }

    public static ServiceError internalServerError() {
        return new ServiceError(500, "The execution of the service failed in some way", "9999");
    }

    public static ServiceError endPointNotFound(String path) {
        return new ServiceError(404, "The endpoint you've requested not found, path:" + path, "9998");
    }

    public Error getError() {
        return error;
    }
}
