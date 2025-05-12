package by.sunserg.grandcapital.service.iservice;

public interface IPhoneDataService {

    void addUserPhone(Long userId, String phone);

    void updateUserPhone(Long userId, Long phoneDataId, String newPhone);

    void deleteUserPhone(Long userId, Long phoneDataId);
}
