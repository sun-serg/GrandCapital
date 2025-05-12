package by.sunserg.grandcapital.service.iservice;

import by.sunserg.grandcapital.service.dto.response.UserSearchResponseDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;


public interface IUserService {

    Page<UserSearchResponseDto> searchUsers(LocalDate dateOfBirth,
                                            String phone,
                                            String name,
                                            String email,
                                            int page,
                                            int size);
}
