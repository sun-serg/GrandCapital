package by.sunserg.grandcapital.service.iservice;

import java.math.BigDecimal;

public interface IAccountService {

    void updateBalances();

    void transferMoney(Long senderId, Long receiverId, BigDecimal amount);
}

