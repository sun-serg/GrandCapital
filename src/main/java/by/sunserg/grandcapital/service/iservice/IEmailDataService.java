package by.sunserg.grandcapital.service.iservice;

public interface IEmailDataService {

    void addUserEmail(Long userId, String email);

    void updateUserEmail(Long userId, Long emailDataId, String newEmail);

    void deleteUserEmail(Long userId, Long emailDataId);
}
