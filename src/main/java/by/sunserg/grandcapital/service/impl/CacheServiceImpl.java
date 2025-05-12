package by.sunserg.grandcapital.service.impl;

import by.sunserg.grandcapital.service.iservice.ICacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements ICacheService {

    @Cacheable(cacheNames = "accountInitialBalance", key = "#userId")
    public BigDecimal getInitialBalance(Long userId, BigDecimal balance) {
        return balance;
    }
}
