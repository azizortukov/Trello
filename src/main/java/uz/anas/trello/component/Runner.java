package uz.anas.trello.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.UserServiceImpl;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    @Value("${init.data}")
    private Boolean initData;

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (initData) {
            userService.save(User.builder()
                    .email("user")
                    .password(passwordEncoder.encode("123"))
                    .firstName("John")
                    .lastName("Wick")
                    .build());
        }
    }
}
