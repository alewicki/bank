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
        if (amount < 0) {
            throw new IllegalArgumentException("Negative amount is not allowed!");
        }
        if (fromEmail.equals(toEmail)) {
            throw new IllegalArgumentException("fromEmail and toEmail cant be equal!");
        }
        Client fromClient = findByEmail(fromEmail);
        Client toClient = findByEmail(toEmail);
        if (fromClient.getBalance() - amount >= 0) {
            fromClient.setBalance(fromClient.getBalance() - amount);
            toClient.setBalance(toClient.getBalance() + amount);
        } else {
            throw new NoSufficientFundsException("Not enough funds!");
        }
    }

}
