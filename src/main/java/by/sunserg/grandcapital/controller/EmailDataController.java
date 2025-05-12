package by.sunserg.grandcapital.controller;

import by.sunserg.grandcapital.service.dto.request.EmailRequestDto;
import by.sunserg.grandcapital.service.iservice.IEmailDataService;
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

@Tag(name = "Контроллер email пользователя", description = "Контроллер для работы со списком email пользователя")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EmailDataController {

    private final IEmailDataService emailDataService;

    @Operation(summary = "Операция добавления email", description = "Операция добавления email в список email пользователя")
    @PostMapping("/{userId}/emails")
    public ResponseEntity<String> addEmail(@PathVariable Long userId,
                                      @RequestBody @Valid EmailRequestDto emailDto,
                                      @AuthenticationPrincipal JwtUser currentUser) {
        if (!currentUser.getUserId().equals(userId)) {
            log.warn(MESSAGE_LOG_FORBIDDEN, currentUser.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MESSAGE_INFO_FORBIDDEN);
        }

        emailDataService.addUserEmail(userId, emailDto.email());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Операция обновления email", description = "Операция обновления email в список email пользователя")
    @PutMapping("/{userId}/emails/{emailDataId}")
    public ResponseEntity<String> updateEmail(@PathVariable Long userId,
                                         @PathVariable Long emailDataId,
                                         @RequestBody @Valid EmailRequestDto emailDto,
                                         @AuthenticationPrincipal JwtUser currentUser) {
        if (!currentUser.getUserId().equals(userId)) {
            log.warn(MESSAGE_LOG_FORBIDDEN, currentUser.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MESSAGE_INFO_FORBIDDEN);
        }

        emailDataService.updateUserEmail(userId, emailDataId, emailDto.email());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Операция удаления email", description = "Операция удаления email из списка email пользователя")
    @DeleteMapping("/{userId}/emails/{emailDataId}")
    public ResponseEntity<String> deleteEmail(@PathVariable Long userId,
                                         @PathVariable Long emailDataId,
                                         @AuthenticationPrincipal JwtUser currentUser) {
        if (!currentUser.getUserId().equals(userId)) {
            log.warn(MESSAGE_LOG_FORBIDDEN, currentUser.getUserId());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MESSAGE_INFO_FORBIDDEN);
        }

        emailDataService.deleteUserEmail(userId, emailDataId);
        return ResponseEntity.ok().build();
    }
}
