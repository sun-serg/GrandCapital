package by.sunserg.grandcapital.service.impl;

import by.sunserg.grandcapital.repository.entity.User;
import by.sunserg.grandcapital.repository.repo.UserRepository;
import by.sunserg.grandcapital.service.dto.response.UserSearchResponseDto;
import by.sunserg.grandcapital.service.iservice.IUserService;
import by.sunserg.grandcapital.service.mapper.UserSearchMapper;
import by.sunserg.grandcapital.service.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final UserSearchMapper userSearchMapper;

    @Override
    @Cacheable(cacheNames = "userSearch",
            key = "#dateOfBirth + '_' + #phone + '_' + #name + '_' + #email + '_' + #page + '_' + #size")
    public Page<UserSearchResponseDto> searchUsers(LocalDate dateOfBirth,
                                                   String phone,
                                                   String name,
                                                   String email,
                                                   int page,
                                                   int size) {
        Specification<User> spec = UserSpecification.userFilters(dateOfBirth, phone, name, email);
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(spec, pageable);
        return userSearchMapper.userPageToUserSearchResponseDtoPage(users);
    }
}
