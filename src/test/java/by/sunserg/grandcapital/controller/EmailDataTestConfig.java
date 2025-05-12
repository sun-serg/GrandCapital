package by.sunserg.grandcapital.controller;

import by.sunserg.grandcapital.service.iservice.IEmailDataService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class EmailDataTestConfig {

    @Bean
    public IEmailDataService emailDataService() {
        return Mockito.mock(IEmailDataService.class);
    }
}
