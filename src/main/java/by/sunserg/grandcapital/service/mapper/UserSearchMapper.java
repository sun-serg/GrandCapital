package by.sunserg.grandcapital.service.mapper;

import by.sunserg.grandcapital.repository.entity.User;
import by.sunserg.grandcapital.service.dto.response.UserSearchResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserSearchMapper {

    UserSearchResponseDto userToUserSearchResponseDto(User user);

    default Page<UserSearchResponseDto> userPageToUserSearchResponseDtoPage(Page<User> users) {
        return users.map(this::userToUserSearchResponseDto);
    }
}
