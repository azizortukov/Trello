package uz.anas.trello.component;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        if (userService.findAll().isEmpty()) {
            userService.save(User.builder()
                    .username("jason")
                    .password(passwordEncoder.encode("123"))
                    .firstName("Jason")
                    .lastName("Rath")
                    .build());
            userService.save(User.builder()
                    .username("benjamin")
                    .password(passwordEncoder.encode("123"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .build());
            userService.save(User.builder()
                    .username("isabella")
                    .password(passwordEncoder.encode("123"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .build());
            userService.save(User.builder()
                    .username("david")
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

    public boolean checkBefore(LocalDateTime date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDateTime.now());
    }

    public boolean checkTaskMembers(List<User> members, UUID userId) {
        return members.stream().anyMatch(member -> member.getId().equals(userId));
    }
}
