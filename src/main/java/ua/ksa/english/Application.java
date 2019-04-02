package ua.ksa.english;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import ua.ksa.english.conf.SecurityConf;

@SpringBootApplication
@Import(SecurityConf.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
