package com.currency.convertor.dao;

import com.currency.convertor.dto.CurrencyRateDto;
import com.currency.convertor.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Specify the entity type (CurrencyRate) as the type parameter
public interface CurrencyRateDao extends JpaRepository<CurrencyRate, Long> {
    List<CurrencyRate> findAll();
}