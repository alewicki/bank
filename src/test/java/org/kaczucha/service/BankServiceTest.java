package org.kaczucha.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.repository.InMemoryClientRepository;

import java.util.HashSet;

public class BankServiceTest {
    private BankService service;

    @BeforeEach
    public void setup() {
        service = new BankService(new InMemoryClientRepository(new HashSet<>()));
    }

    @Test
    public void test() {

    }
}
