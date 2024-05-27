package uz.anas.trello.component;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    @Value("${init.data}")
    private Boolean initData;

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        if (initData) {
            userService.save(User.builder()
                    .email("user0")
                    .password(passwordEncoder.encode("123"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .build());
            userService.save(User.builder()
                    .email("user1")
                    .password(passwordEncoder.encode("123"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .build());
            userService.save(User.builder()
                    .email("user2")
                    .password(passwordEncoder.encode("123"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .build());
            userService.save(User.builder()
                    .email("user3")
                    .password(passwordEncoder.encode("123"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .build());
        }
    }

    public String dateFormat(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }
}
