package by.sunserg.grandcapital.service.jwt;

import by.sunserg.grandcapital.repository.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtUserDetailsService {

    private final UserRepository userRepository;

    public JwtUser loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .map(JwtUserFactory::create)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь с id %s не найден", id)));
    }
}
