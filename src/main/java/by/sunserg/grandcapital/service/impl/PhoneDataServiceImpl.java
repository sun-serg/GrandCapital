package by.sunserg.grandcapital.service.impl;

import by.sunserg.grandcapital.controller.exception.DataNotFoundException;
import by.sunserg.grandcapital.controller.exception.NotUniqueDataException;
import by.sunserg.grandcapital.repository.entity.PhoneData;
import by.sunserg.grandcapital.repository.entity.User;
import by.sunserg.grandcapital.repository.repo.PhoneDataRepository;
import by.sunserg.grandcapital.repository.repo.UserRepository;
import by.sunserg.grandcapital.service.iservice.IPhoneDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static by.sunserg.grandcapital.service.util.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneDataServiceImpl implements IPhoneDataService {

    private final UserRepository userRepository;

    private final PhoneDataRepository phoneDataRepository;

    @Override
    @Transactional
    public void addUserPhone(Long userId, String phone) {
        if (phoneDataRepository.findByPhone(phone).isPresent()) {
            log.info(LOG_NOT_UNIQUE_PHONE_ERROR_MESSAGE, phone);
            throw new NotUniqueDataException(String.format(NOT_UNIQUE_PHONE_ERROR_MESSAGE, phone));
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, userId)));

        PhoneData phoneData = PhoneData.builder()
                .phone(phone)
                .user(user)
                .build();

        user.getPhoneData().add(phoneData);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserPhone(Long userId, Long phoneDataId, String newPhone) {
        if (phoneDataRepository.findByPhone(newPhone).isPresent()) {
            log.info(LOG_NOT_UNIQUE_PHONE_ERROR_MESSAGE, newPhone);
            throw new NotUniqueDataException(String.format(NOT_UNIQUE_PHONE_ERROR_MESSAGE, newPhone));
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, userId)));

        PhoneData phoneData = user.getPhoneData().stream()
                .filter(ed -> ed.getId().equals(phoneDataId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException(String.format(PHONE_NOT_FOUND_MESSAGE, userId)));

        phoneData.setPhone(newPhone);
        userRepository.save(user);
    }

    @Override
    public void deleteUserPhone(Long userId, Long phoneDataId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(String.format(USER_ID_NOT_FOUND_MESSAGE, userId)));

        boolean removed = user.getPhoneData()
                .removeIf(ed -> ed.getId().equals(phoneDataId));

        if (!removed) {
            log.info(PHONE_ID_NOT_FOUND_LOG_MESSAGE, phoneDataId, userId);
            throw new DataNotFoundException(String.format(PHONE_NOT_FOUND_MESSAGE, userId));
        }

        userRepository.save(user);
    }
}
