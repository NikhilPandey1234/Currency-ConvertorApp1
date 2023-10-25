package com.currency.convertor.exceptions;

public class CurrencyNotConvertedException extends RuntimeException {
    public CurrencyNotConvertedException(String currencyConversionFailed) {
        super(currencyConversionFailed);
    }
}
