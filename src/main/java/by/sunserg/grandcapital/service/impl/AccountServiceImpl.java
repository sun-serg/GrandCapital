package by.sunserg.grandcapital.service.impl;

import by.sunserg.grandcapital.controller.exception.DataNotFoundException;
import by.sunserg.grandcapital.controller.exception.MoneyTransferException;
import by.sunserg.grandcapital.repository.entity.Account;
import by.sunserg.grandcapital.repository.repo.AccountRepository;
import by.sunserg.grandcapital.service.iservice.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static by.sunserg.grandcapital.service.util.Constants.*;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;

    private final CacheServiceImpl cacheServiceImpl;

    @Override
    @Transactional
    @Scheduled(fixedRate = 30000)
    public void updateBalances() {
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            BigDecimal initialDeposit = cacheServiceImpl.getInitialBalance(account.getId(), account.getBalance());

            BigDecimal maxBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));
            BigDecimal currentBalance = account.getBalance();
            BigDecimal candidate = currentBalance.multiply(BigDecimal.valueOf(1.1));

            if (candidate.compareTo(maxBalance) > 0) {
                candidate = maxBalance;
            }
            account.setBalance(candidate);
        }

        accountRepository.saveAll(accounts);
    }

    @Override
    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new MoneyTransferException(MONEY_TRANSFER_AMOUNT_ERROR_MESSAGE);
        }

        Long firstUserId = senderId.compareTo(receiverId) < 0 ? senderId : receiverId;
        Long secondUserId = senderId.compareTo(receiverId) < 0 ? receiverId : senderId;

        Account firstAccount  = accountRepository.findByUserIdForUpdate(firstUserId)
                .orElseThrow(() -> new DataNotFoundException(SENDER_ACCOUNT_NOT_FOUND_MESSAGE));
        Account secondAccount   = accountRepository.findByUserIdForUpdate(secondUserId)
                .orElseThrow(() -> new DataNotFoundException(RECEIVER_ACCOUNT_NOT_FOUND_MESSAGE));

        Account senderAccount = senderId.equals(firstUserId) ? firstAccount : secondAccount;
        Account receiverAccount = senderId.equals(firstUserId) ? secondAccount : firstAccount;

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new MoneyTransferException(MONEY_TRANSFER_NOT_ENOUGH_MONEY_MESSAGE);
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
    }
}
