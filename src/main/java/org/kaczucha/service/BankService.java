package org.kaczucha.service;

import org.kaczucha.Client;
import org.kaczucha.repository.ClientRepository;

public class BankService {
    private final ClientRepository clientRepository;

    public BankService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public void transfer(
            String fromEmail,
            String toEmail,
            double amount
    ) {

    }

}
