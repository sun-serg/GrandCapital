package by.sunserg.grandcapital.controller;

import by.sunserg.grandcapital.service.dto.request.PhoneRequestDto;
import by.sunserg.grandcapital.service.iservice.IPhoneDataService;
import by.sunserg.grandcapital.service.jwt.JwtUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.sunserg.grandcapital.service.util.Constants.MESSAGE_LOG_FORBIDDEN;
import static by.sunserg.grandcapital.service.util.Constants.MESSAGE_INFO_FORBIDDEN;

@Tag(name = "Контроллер телефонов пользователя", description = "Контроллер для работы со списком номеров телефона пользователя")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PhoneDataController {

    private final IPhoneDataService phoneDataService;

    @Operation(summary = "Операция добавления телефона", description = "Операция добавления номера телефона в список телефонов пользователя")
    @PostMapping("/{userId}/phones")
    public ResponseEntity<String> addPhone(@PathVariable Long userId,
                                      @RequestBody @Valid PhoneRequestDto phoneDto,
                                      @AuthenticationPrincipal JwtUser currentUser) {
        if (!currentUser.getUserId().equals(userId)) {
            log.warn(MESSAGE_LOG_FORBIDDEN, currentUser.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MESSAGE_INFO_FORBIDDEN);
        }

        phoneDataService.addUserPhone(userId, phoneDto.phone());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Операция обновления телефона", description = "Операция обновления номера телефона в списке телефонов пользователя")
    @PutMapping("/{userId}/phones/{phoneDataId}")
    public ResponseEntity<String> updatePhone(@PathVariable Long userId,
                                         @PathVariable Long phoneDataId,
                                         @RequestBody @Valid PhoneRequestDto phoneDto,
                                         @AuthenticationPrincipal JwtUser currentUser) {
        if (!currentUser.getUserId().equals(userId)) {
            log.warn(MESSAGE_LOG_FORBIDDEN, currentUser.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MESSAGE_INFO_FORBIDDEN);
        }

        phoneDataService.updateUserPhone(userId, phoneDataId, phoneDto.phone());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Операция удаления телефона", description = "Операция удаления номера телефона из списка телефонов пользователя")
    @DeleteMapping("/{userId}/phones/{phoneDataId}")
    public ResponseEntity<String> deletePhone(@PathVariable Long userId,
                                         @PathVariable Long phoneDataId,
                                         @AuthenticationPrincipal JwtUser currentUser) {
        if (!currentUser.getUserId().equals(userId)) {
            log.warn(MESSAGE_LOG_FORBIDDEN, currentUser.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MESSAGE_INFO_FORBIDDEN);
        }

        phoneDataService.deleteUserPhone(userId, phoneDataId);
        return ResponseEntity.ok().build();
    }
}
