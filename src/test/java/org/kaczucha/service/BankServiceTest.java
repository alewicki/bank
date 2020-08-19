package org.kaczucha.service;

import org.junit.jupiter.api.*;
import org.kaczucha.repository.ClientRepository;
import org.kaczucha.repository.entity.*;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

public class BankServiceTest {
    private BankService service;
    private ClientRepository repository;


    @BeforeEach
    public void setup() {
        repository = mock(ClientRepository.class);
        service = new BankService(repository);
    }

    @Test
    public void transfer_allParamsOk_fundsTransferred() {
        //given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(1000, "PLN"))
        );
        final Client clientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(500, "PLN"))
        );
        final double amount = 100;

        when(repository.findByEmail(emailFrom))
                .thenReturn(clientFrom);
        when(repository.findByEmail(emailTo))
                .thenReturn(clientTo);

        //when
        service.transfer(emailFrom, emailTo, amount);
        //then
        final Client expectedClientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(900, "PLN"))
        );
        final Client expectedClientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(600, "PLN"))
        );

        verify(repository, times(1)).save(expectedClientFrom);
        verify(repository, times(1)).save(expectedClientTo);

    }

    @Test
    public void transfer_allFounds_fundsTransferred() {
        // given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(1000, "PLN"))
        );
        final Client clientTo = new Client("Bartek",
                emailTo,
                singletonList(new Account(500, "PLN"))
        );
        final double amount = 1000;
        when(repository.findByEmail(emailFrom)).thenReturn(clientFrom);
        when(repository.findByEmail(emailTo)).thenReturn(clientTo);
        // when
        service.transfer(emailFrom, emailTo, amount);
        // then
        final Client expectedClientFrom = new Client(
                "Alek",
                emailFrom,
                singletonList(new Account(0, "PLN"))
        );
        final Client expectedClientTo = new Client(
                "Bartek",
                emailTo,
                singletonList(new Account(1500, "PLN"))
        );
        verify(repository).save(expectedClientFrom);
        verify(repository).save(expectedClientTo);
    }

    @Test
    public void transfer_notEnoughFunds_thrownNoSufficientFundsException() {
        // given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final Client clientFrom = new Client("Alek",
                emailFrom,
                singletonList(new Account(100, "PLN")));
        final Client clientTo = new Client("Bartek",
                emailTo,
                singletonList(new Account(500, "PLN")));

        final double amount = 1000;
        when(repository.findByEmail(emailFrom)).thenReturn(clientFrom);
        when(repository.findByEmail(emailTo)).thenReturn(clientTo);
        // when/then
        Assertions.assertThrows(
                NoSufficientFundsException.class,
                () -> service.transfer(emailFrom, emailTo, amount)
        );
    }

    @Test
    public void transfer_negativeAmount_thrownIllegalArgumentException() {
        // given
        final String emailFrom = "a@a.pl";
        final String emailTo = "b@b.pl";
        final double amount = -1000;

        // when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(emailFrom, emailTo, amount)
        );
    }

    @Test
    public void transfer_toSameClient_thrownException() {
        //given
        final String email = "a@a.pl";
        // when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.transfer(email, email, 10)
        );
    }

    @Test
    public void withdraw_correctAmount_balanceChangedCorrectly() {
        //given
        final String email = "a@a.pl";
        final Client client = new Client("Alek", email, singletonList(new Account(100, "PLN")));

        //when
        when(repository.findByEmail(email)).thenReturn(client);
        service.withdraw(email, 50);
        //then

        Client expectedClient = new Client("Alek", email, singletonList(new Account(50, "PLN")));
        verify(repository).save(expectedClient);
    }
//
//    @Test
//    public void withdraw_correctFloatingPointAmount_balanceChangedCorrectly() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client("Alek", email, 100);
//        clients.add(client);
//        //when
//        service.withdraw(email, 50.5);
//        //then
//        Client expectedClient = new Client("Alek", email, 49.5);
//        final Client actualClient = clients.get(0);
//        Assertions.assertEquals(expectedClient, actualClient);
//    }
//
//    @Test
//    public void withdraw_allBalance_balanceSetToZero() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client("Alek", email, 100);
//        clients.add(client);
//        service.withdraw(email, 100);
//        //when
//        //then
//        Client expectedClient = new Client("Alek", email, 0);
//        final Client actualClient = clients.get(0);
//        Assertions.assertEquals(expectedClient, actualClient);
//    }
//
//    @Test
//    public void withdraw_negativeAmount_throwsIllegalArgumentException() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client("Alek", email, 100);
//        clients.add(client);
//        final int amount = -100;
//        //when
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client("Alek", email, 100);
//        clients.add(client);
//        final int amount = 0;
//        //when
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_amountBiggerThenBalance_throwsNoSufficientFundsException() {
//        //given
//        final String email = "a@a.pl";
//        final Client client = new Client("Alek", email, 100);
//        clients.add(client);
//        final int amount = 1000;
//        //when
//        Assertions.assertThrows(
//                NoSufficientFundsException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//    @Test
//    public void withdraw_incorrectEmail_throwsNoSuchElementException() {
//        //given
//        final String email = "incorrect_email@a.pl";
//        final int amount = 1000;
//        //when/then
//        Assertions.assertThrows(
//                NoSuchElementException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//
//
//    @Test
//    public void withdraw_upperCaseEmail_balanceChangedCorrectly() {
//        //given
//        final String email = "A@A.PL";
//        final Client client = new Client("Alek", "a@a.pl", 100);
//        clients.add(client);
//        //when
//        service.withdraw(email, 50);
//        //then
//        Client expectedClient = new Client("Alek", "a@a.pl", 50);
//        final Client actualClient = clients.get(0);
//        Assertions.assertEquals(expectedClient, actualClient);
//    }
//
//    @Test
//    public void withdraw_nullEmail_throwsIllegalArgumentException() {
//        //given
//        final String email = null;
//        final int amount = 1000;
//        //when/then
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> service.withdraw(email, amount)
//        );
//    }
//

}
