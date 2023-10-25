package com.currency.convertor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency_rate")
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_currency")
    private String fromCurrency;
    @Column(name = "to_currency")
    private String toCurrency;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "date")
    private String date;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Column(name = "conversion_result")
    private String conversionResult;
    @Column(name = "today_Rate")
    private BigDecimal todayRate;
}
