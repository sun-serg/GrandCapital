package by.sunserg.grandcapital.service.dto.response;

import java.io.Serializable;
import java.time.LocalDate;


public record UserSearchResponseDto(String name, LocalDate dateOfBirth) implements Serializable {
}
