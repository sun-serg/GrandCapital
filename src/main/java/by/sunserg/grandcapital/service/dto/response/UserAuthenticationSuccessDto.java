package by.sunserg.grandcapital.service.dto.response;

import java.io.Serializable;

public record UserAuthenticationSuccessDto(String userId, String token) implements UserAuthenticationResponseDto, Serializable {
}
