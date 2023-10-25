package com.currency.convertor.services;

import com.currency.convertor.dto.CurrencyRateDto;

import java.util.List;

public interface CurrencyRateService {

    void saveCurrency(CurrencyRateDto currencyRateDto);

    public List<CurrencyRateDto> getAllData();
}
