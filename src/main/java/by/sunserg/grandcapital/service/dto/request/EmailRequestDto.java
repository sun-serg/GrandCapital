package by.sunserg.grandcapital.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

import static by.sunserg.grandcapital.service.util.Constants.EMAIL_VALIDATION_PATTERN;


public record EmailRequestDto(@Pattern(regexp = EMAIL_VALIDATION_PATTERN, message = "Неверный формат email, пример: example@example.com")
                              @NotBlank(message = "Поле не должен быть null")
                              String email) implements Serializable {
}
