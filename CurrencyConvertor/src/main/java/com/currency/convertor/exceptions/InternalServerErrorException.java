package com.currency.convertor.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String apiRequestFailed) {
        super(apiRequestFailed);
    }
}
