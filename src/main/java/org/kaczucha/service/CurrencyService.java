package org.kaczucha.service;

import lombok.RequiredArgsConstructor;
import org.kaczucha.service.dto.CurrencyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final RestTemplate restTemplate;

    public CurrencyResponse getCurrencyRates() {
        final HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("base", "USD");
        final ResponseEntity<CurrencyResponse> response = restTemplate
                .getForEntity(
                        "https://api.exchangeratesapi.io/latest",
                        CurrencyResponse.class,
                        uriVariables
                );
        return response.getBody();

    }
}
