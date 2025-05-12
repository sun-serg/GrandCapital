package by.sunserg.grandcapital.service.iservice;


import by.sunserg.grandcapital.service.dto.request.UserAuthenticationRequestDto;
import by.sunserg.grandcapital.service.dto.response.UserAuthenticationDto;

public interface IAuthenticationService {

    UserAuthenticationDto findByEmailAndPassword(UserAuthenticationRequestDto requestDto);
}
