package org.kaczucha;

import org.kaczucha.repository.ClientSpringJpaRepository;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.kaczucha.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private final BankService bankService;
    private final ClientSpringJpaRepository repository;

    @Autowired
    public Main(
            BankService bankService,
            ClientSpringJpaRepository repository
    ) {
        this.bankService = bankService;
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {

        final List<Client> list = repository.findByName("Bartek");
        list.forEach(System.out::println);

        final Page<Client> page0 = repository.findByName("Bartek", PageRequest.of(0, 1, Sort.by("email").descending()));
        page0.getContent().forEach(System.out::println);
        final int totalPages = page0.getTotalPages();

        for (int i = 1; i < totalPages; i++) {
            final Page<Client> page = repository.findByName(
                    "Bartek",
                    PageRequest.of(i, 1,Sort.by("email").descending()
                    ));
            page.getContent().forEach(System.out::println);
        }

        System.out.println("=============");
        final List<Client> allByName = repository.findAll(Sort.by("name"));
        allByName.forEach(System.out::println);


//        try (Scanner scanner = new Scanner(System.in)) {
//            while (true) {
//                System.out.println("1 - add user");
//                System.out.println("2 - find user");
//                System.out.println("3 - exit app");
//                final String next = scanner.next();
//                if (next.equals("1")) {
//                    addUser(scanner);
//                }
//                if (next.equals("2")) {
//                    printUser(scanner);
//                }
//                if (next.equals("3")) {
//                    break;
//                }
//            }
//        }
    }

    private void printUser(Scanner scanner) {
        System.out.println("Enter email:");
        final String mail = scanner.next();
        final Client byEmail = bankService.findByEmail(mail);
        System.out.println(byEmail);
    }


    private void addUser(Scanner scanner) {
        System.out.println("Enter name:");
        final String name = scanner.next();
        System.out.println("Enter email:");
        final String mail = scanner.next();
        System.out.println("Enter balance:");
        final double balance = scanner.nextDouble();
        final Account account = new Account(balance, "PLN");
        final List<Account> accounts = List.of(account);
        bankService.save(new Client(name, mail, accounts));
    }


}
