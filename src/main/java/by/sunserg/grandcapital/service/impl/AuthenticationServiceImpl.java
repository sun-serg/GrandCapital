package by.sunserg.grandcapital.service.impl;

import by.sunserg.grandcapital.repository.entity.User;
import by.sunserg.grandcapital.repository.repo.UserRepository;
import by.sunserg.grandcapital.service.dto.request.UserAuthenticationRequestDto;
import by.sunserg.grandcapital.service.dto.response.UserAuthenticationDto;
import by.sunserg.grandcapital.service.iservice.IAuthenticationService;
import by.sunserg.grandcapital.service.mapper.UserAuthenticationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static by.sunserg.grandcapital.service.util.Constants.USER_FOUND_LOG_MESSAGE;
import static by.sunserg.grandcapital.service.util.Constants.LOG_USER_NOT_FOUND_MESSAGE;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;

    private final UserAuthenticationMapper userAuthenticationMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserAuthenticationDto findByEmailAndPassword(UserAuthenticationRequestDto requestDto) {
        User user = userRepository.findUserByEmail(requestDto.email());

        if (user != null && passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            log.info(USER_FOUND_LOG_MESSAGE, requestDto.email());
            return userAuthenticationMapper.userToUserAuthenticationResponseDto(user);
        }

        log.warn(LOG_USER_NOT_FOUND_MESSAGE, requestDto.email());
        return null;
    }
}
