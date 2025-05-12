package by.sunserg.grandcapital.service.iservice;


import java.math.BigDecimal;

public interface ICacheService {

    BigDecimal getInitialBalance(Long userId, BigDecimal balance);
}
