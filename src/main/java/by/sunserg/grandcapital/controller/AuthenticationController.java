package by.sunserg.grandcapital.controller;

import by.sunserg.grandcapital.service.dto.request.UserAuthenticationRequestDto;
import by.sunserg.grandcapital.service.dto.response.UserAuthenticationDto;
import by.sunserg.grandcapital.service.dto.response.UserAuthenticationFailureDto;
import by.sunserg.grandcapital.service.dto.response.UserAuthenticationResponseDto;
import by.sunserg.grandcapital.service.dto.response.UserAuthenticationSuccessDto;
import by.sunserg.grandcapital.service.impl.AuthenticationServiceImpl;
import by.sunserg.grandcapital.service.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.sunserg.grandcapital.service.util.Constants.AUTH_ERROR_INFO_MESSAGE;
import static by.sunserg.grandcapital.service.util.Constants.AUTH_ERROR_LOG_MESSAGE;

@Tag(name = "Контроллер аутентификации", description = "Контроллер для аутентификации пользователей и создания JWT-токена")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationServiceImpl authenticationService;

    @Operation(summary = "Операция аутентификации пользователя", description = "Операция аутентификации пользователя с получением JWT-токена")
    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationResponseDto> login(@Valid @RequestBody UserAuthenticationRequestDto requestDto) {
        UserAuthenticationDto user = authenticationService.findByEmailAndPassword(requestDto);

        if (user == null) {
            log.warn(AUTH_ERROR_LOG_MESSAGE, requestDto.email());
            UserAuthenticationFailureDto failureDto = new UserAuthenticationFailureDto(AUTH_ERROR_INFO_MESSAGE);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(failureDto);
        }

        UserAuthenticationSuccessDto successDto = new UserAuthenticationSuccessDto(user.userId(),
                jwtTokenProvider.createToken(user));
        return ResponseEntity.ok(successDto);
    }
}
