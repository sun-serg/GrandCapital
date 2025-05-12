package by.sunserg.grandcapital.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

import static by.sunserg.grandcapital.service.util.Constants.EMAIL_VALIDATION_PATTERN;

public record UserAuthenticationRequestDto(
        @NotBlank(message = "Email обязателен")
        @Pattern(regexp = EMAIL_VALIDATION_PATTERN, message = "Неверный email")
        String email,

        @NotBlank(message = "Пароль обязателен")
        String password) implements Serializable {
}
