package by.sunserg.grandcapital.controller;

import by.sunserg.grandcapital.service.iservice.IAccountService;
import by.sunserg.grandcapital.service.jwt.JwtUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static by.sunserg.grandcapital.service.util.Constants.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Контроллер счетов", description = "Контроллер для работы со счетами пользователей")
public class AccountController {

    private final IAccountService accountService;

    @PostMapping("/transfer")
    @Operation(summary = "Операция перевода средств", description = "Операция перевода средств со счета одного пользователя на счет другого")
    public ResponseEntity<String> transfer(@RequestParam("receiverId") Long receiverId,
                                           @RequestParam("amount") BigDecimal amount,
                                           @AuthenticationPrincipal JwtUser currentUser) {
        Long senderId = currentUser.getUserId();

        if (senderId.equals(receiverId)) {
            log.warn(MONEY_TRANSFER_LOG_ERROR_MESSAGE, currentUser.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(MONEY_TRANSFER_ERROR_INFO_MESSAGE);
        }

        accountService.transferMoney(senderId, receiverId, amount);
        return ResponseEntity.ok(MONEY_TRANSFER_SUCCESS_MESSAGE);
    }
}
