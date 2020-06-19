package org.kaczucha.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kaczucha.Client;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryClientRepositoryTest {
    private InMemoryClientRepository repository;
    private Set<Client> clients;

    @BeforeEach
    public void setup() {
        clients = new HashSet<>();
        repository = new InMemoryClientRepository(clients);
    }

    @Test
    public void verifyIfUserIsAddingCorrectlyToTheRepository() {
        //given
        final Client client = new Client("Alek", "a@a.pl", 100);
        final Client expectedClient = new Client("Alek", "a@a.pl", 100);
        //when
        repository.save(client);
        //then
        final Client actualClient = clients.stream().findFirst().get();
        assertEquals(expectedClient, actualClient);
    }


}
