package org.kaczucha.controller;

import lombok.RequiredArgsConstructor;
import org.kaczucha.service.CurrencyService;
import org.kaczucha.service.dto.CurrencyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService service;

    @GetMapping(path = "/api/currency")
    public ResponseEntity<CurrencyResponse> getCurrencyRates(){
        final CurrencyResponse currencyRates = service.getCurrencyRates();
        return new ResponseEntity<>(currencyRates, HttpStatus.ACCEPTED);

    }

}
