package by.sunserg.grandcapital.service.dto.response;

import java.io.Serializable;

public record UserAuthenticationFailureDto(String errorMessage) implements UserAuthenticationResponseDto, Serializable {
}
