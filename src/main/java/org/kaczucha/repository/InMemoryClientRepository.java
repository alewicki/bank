package org.kaczucha.repository;

import org.kaczucha.Client;

import java.util.Objects;
import java.util.Set;

public class InMemoryClientRepository implements ClientRepository {
    private Set<Client> clients;

    public InMemoryClientRepository(Set<Client> clients) {
        this.clients = clients;
    }

    public void save(Client client) {
        clients.add(client);
    }

    public Client findByEmail(String email) {
        return clients
                .stream()
                .filter(client -> Objects.equals(client.getEmail(), email))
                .findFirst()
                .get();
    }
}
