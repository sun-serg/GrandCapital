package by.sunserg.grandcapital.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static by.sunserg.grandcapital.service.util.Constants.PHONE_VALIDATION_PATTERN;

public record PhoneRequestDto(@Pattern(regexp = PHONE_VALIDATION_PATTERN, message = "Неверный формат номера телефона, пример: 79207865432")
                              @NotBlank(message = "Поле не должно быть пустым")
                              String phone) {
}
