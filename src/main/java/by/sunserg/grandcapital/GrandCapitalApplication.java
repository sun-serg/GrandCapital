package by.sunserg.grandcapital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GrandCapitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrandCapitalApplication.class, args);
    }

}
