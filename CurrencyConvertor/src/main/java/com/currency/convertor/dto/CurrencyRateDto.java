package com.currency.convertor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRateDto {

    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private String date;

    private LocalDateTime timestamp;
    private String conversionResult;
    private BigDecimal todayRate;
}
