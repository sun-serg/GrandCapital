package by.sunserg.grandcapital.service.impl;

import by.sunserg.grandcapital.controller.exception.DataNotFoundException;
import by.sunserg.grandcapital.controller.exception.MoneyTransferException;
import by.sunserg.grandcapital.repository.entity.Account;
import by.sunserg.grandcapital.repository.entity.User;
import by.sunserg.grandcapital.repository.repo.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private static final String MONEY_TRANSFER_AMOUNT_ERROR_MESSAGE = "Сумма перевода должна быть положительной";
    private static final String SENDER_ACCOUNT_NOT_FOUND_MESSAGE = "Аккаунт отправителя не найден";
    private static final String RECEIVER_ACCOUNT_NOT_FOUND_MESSAGE = "Аккаунт получателя не найден";
    private static final String MONEY_TRANSFER_NOT_ENOUGH_MONEY_MESSAGE = "Недостаточно средств для перевода";

    @Test
    void testSuccessfulTransfer() {
        Long senderId = 2L;
        Long receiverId = 1L;
        BigDecimal transferAmount = new BigDecimal("100.00");

        Account senderAccount = new Account();
        senderAccount.setBalance(new BigDecimal("200.00"));
        senderAccount.setUser(new User());

        Account receiverAccount = new Account();
        receiverAccount.setBalance(new BigDecimal("50.00"));
        receiverAccount.setUser(new User());

        when(accountRepository.findByUserIdForUpdate(senderId))
                .thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserIdForUpdate(receiverId))
                .thenReturn(Optional.of(receiverAccount));

        accountService.transferMoney(senderId, receiverId, transferAmount);

        assertEquals(new BigDecimal("100.00"), senderAccount.getBalance(),
                "Баланс отправителя должен стать равен 100.00");
        assertEquals(new BigDecimal("150.00"), receiverAccount.getBalance(),
                "Баланс получателя должен стать равен 150.00");
    }

    @Test
    void testTransferWithNullAmount() {
        Long senderId = 1L;
        Long receiverId = 2L;

        MoneyTransferException exception = assertThrows(MoneyTransferException.class,
                () -> accountService.transferMoney(senderId, receiverId, null));

        assertEquals(MONEY_TRANSFER_AMOUNT_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testTransferWithZeroAmount() {
        Long senderId = 1L;
        Long receiverId = 2L;
        BigDecimal transferAmount = BigDecimal.ZERO;

        MoneyTransferException exception = assertThrows(MoneyTransferException.class,
                () -> accountService.transferMoney(senderId, receiverId, transferAmount));

        assertEquals(MONEY_TRANSFER_AMOUNT_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void testSenderAccountNotFound() {
        Long senderId = 1L;
        Long receiverId = 2L;
        BigDecimal transferAmount = new BigDecimal("100.00");

        when(accountRepository.findByUserIdForUpdate(senderId))
                .thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> accountService.transferMoney(senderId, receiverId, transferAmount));

        assertEquals(SENDER_ACCOUNT_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    void testReceiverAccountNotFound() {
        Long senderId = 1L;
        Long receiverId = 2L;

        BigDecimal transferAmount = new BigDecimal("100.00");
        Account senderAccount = new Account();
        senderAccount.setBalance(new BigDecimal("200.00"));
        senderAccount.setUser(new User());

        when(accountRepository.findByUserIdForUpdate(senderId))
                .thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserIdForUpdate(receiverId))
                .thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> accountService.transferMoney(senderId, receiverId, transferAmount));

        assertEquals(RECEIVER_ACCOUNT_NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    void testNotEnoughMoney() {
        Long senderId = 2L;
        Long receiverId = 1L;

        BigDecimal transferAmount = new BigDecimal("100.00");
        Account senderAccount = new Account();
        senderAccount.setBalance(new BigDecimal("50.00"));
        senderAccount.setUser(new User());
        Account receiverAccount = new Account();
        receiverAccount.setBalance(new BigDecimal("50.00"));
        receiverAccount.setUser(new User());

        when(accountRepository.findByUserIdForUpdate(senderId))
                .thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByUserIdForUpdate(receiverId))
                .thenReturn(Optional.of(receiverAccount));

        MoneyTransferException exception = assertThrows(MoneyTransferException.class,
                () -> accountService.transferMoney(senderId, receiverId, transferAmount));

        assertEquals(MONEY_TRANSFER_NOT_ENOUGH_MONEY_MESSAGE, exception.getMessage());
    }
}