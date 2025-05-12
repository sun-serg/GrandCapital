package by.sunserg.grandcapital.service.mapper;

import by.sunserg.grandcapital.repository.entity.User;
import by.sunserg.grandcapital.service.dto.response.UserAuthenticationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    @Mapping(target = "userId", source = "id")
    UserAuthenticationDto userToUserAuthenticationResponseDto(User user);
}
