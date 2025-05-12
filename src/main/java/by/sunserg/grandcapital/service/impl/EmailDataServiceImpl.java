package by.sunserg.grandcapital.service.impl;

import by.sunserg.grandcapital.controller.exception.DataNotFoundException;
import by.sunserg.grandcapital.controller.exception.NotUniqueDataException;
import by.sunserg.grandcapital.repository.entity.EmailData;
import by.sunserg.grandcapital.repository.entity.User;
import by.sunserg.grandcapital.repository.repo.EmailDataRepository;
import by.sunserg.grandcapital.repository.repo.UserRepository;
import by.sunserg.grandcapital.service.iservice.IEmailDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static by.sunserg.grandcapital.service.util.Constants.*;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailDataServiceImpl implements IEmailDataService {

    private final UserRepository userRepository;

    private final EmailDataRepository emailDataRepository;

    @Override
    public void addUserEmail(Long userId, String email) {
        if (emailDataRepository.findByEmail(email).isPresent()) {
            log.warn(LOG_NOT_UNIQUE_EMAIL_ERROR_MESSAGE, email);
            throw new NotUniqueDataException(String.format(NOT_UNIQUE_EMAIL_ERROR_MESSAGE, email));
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, userId)));

        EmailData emailData = EmailData.builder()
                .email(email)
                .user(user)
                .build();

        user.getEmailData().add(emailData);
        userRepository.save(user);
    }

    @Override
    public void updateUserEmail(Long userId, Long emailDataId, String newEmail) {
        if (emailDataRepository.findByEmail(newEmail).isPresent()) {
            log.warn(LOG_NOT_UNIQUE_EMAIL_ERROR_MESSAGE, newEmail);
            throw new NotUniqueDataException(String.format(NOT_UNIQUE_EMAIL_ERROR_MESSAGE, newEmail));
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, userId)));

        EmailData emailData = user.getEmailData().stream()
                .filter(ed -> ed.getId().equals(emailDataId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, userId)));

        emailData.setEmail(newEmail);
        userRepository.save(user);
    }

    @Override
    public void deleteUserEmail(Long userId, Long emailDataId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, userId)));

        boolean removed = user.getEmailData()
                .removeIf(ed -> ed.getId().equals(emailDataId));

        if (!removed) {
            log.warn(EMAIL_ID_NOT_FOUND_LOG_MESSAGE, emailDataId, userId);
            throw new DataNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, userId));
        }

        userRepository.save(user);
    }
}
