package kr.ac.ssu.wherealarmyouserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kr.ac.ssu.wherealarmyouserver.message")
public class WhereAlarmYouServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhereAlarmYouServerApplication.class, args);
    }

}
