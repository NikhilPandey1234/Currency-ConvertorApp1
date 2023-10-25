package com.currency.convertor.services.impl;

import com.currency.convertor.dao.CurrencyRateDao;
import com.currency.convertor.dto.CurrencyRateDto;
import com.currency.convertor.entity.CurrencyRate;
import com.currency.convertor.exceptions.CurrencyConversionException;
import com.currency.convertor.exceptions.CurrencyNotConvertedException;
import com.currency.convertor.exceptions.InternalServerErrorException;
import com.currency.convertor.services.CurrencyRateService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    @Autowired
    private CurrencyRateDao currencyRateDao;

    @Autowired
    private ModelMapper modelMapper;

    private final OkHttpClient client = new OkHttpClient();


    @Override
    public void saveCurrency(CurrencyRateDto currencyRateDto) {
        //CurrencyRate currencyRate = this.dtoToCurrencyRate(currencyRateDto);
        try {
            CurrencyRate currencyRate = this.dtoToCurrencyRate(currencyRateDto);

            String apiUrl = "https://api.apilayer.com/fixer/convert?" +
                    "to=" + currencyRate.getToCurrency() +
                    "&from=" + currencyRate.getFromCurrency() +
                    "&amount=" + currencyRate.getAmount() +
                    "&date=" + currencyRate.getDate();

            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("apikey", "8JpJ9HZ4x6C2mvmAHTIVtMVOFUF1wLZT")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> jsonResponse = jsonParser.parseMap(responseBody);

                if (jsonResponse.containsKey("result")) {
                    BigDecimal convertedAmount = new BigDecimal(jsonResponse.get("result").toString());
                    BigDecimal todayRate = jsonResponse.containsKey("info") ?
                            new BigDecimal(((Map<String, Object>) jsonResponse.get("info")).get("rate").toString()) : BigDecimal.ZERO;

                    currencyRate.setConversionResult(convertedAmount.toString());
                    currencyRate.setTodayRate(todayRate);

                    currencyRateDao.save(currencyRate);
                } else {
                    // Handle currency not converted error
                    throw new CurrencyNotConvertedException("Currency conversion failed");
                }
            } else {
                // Handle internal server error
                throw new InternalServerErrorException("API request failed");
            }
        } catch (IOException e) {
            // Handle other exceptions
            throw new CurrencyConversionException("Currency conversion exception: " + e.getMessage());
        }
    }


    @Override
    public List<CurrencyRateDto> getAllData() {
        List<CurrencyRate> currencyRates = currencyRateDao.findAll();
        List<CurrencyRateDto> currencyRateDtos = new ArrayList<>();

        for (CurrencyRate currencyRate : currencyRates) {
            CurrencyRateDto currencyRateDto = currencyRateToDto(currencyRate);
            currencyRateDtos.add(currencyRateDto);
        }

        return currencyRateDtos;
    }

    public CurrencyRate dtoToCurrencyRate(CurrencyRateDto currencyRateDto)
    {
        CurrencyRate currencyRate = this.modelMapper.map(currencyRateDto,CurrencyRate.class);
        return currencyRate;
    }

    public CurrencyRateDto currencyRateToDto(CurrencyRate currencyRate)
    {
        CurrencyRateDto currencyRateDto = this.modelMapper.map(currencyRate,CurrencyRateDto.class);
        return currencyRateDto;
    }
}
