package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import uz.anas.trello.entity.User;
import uz.anas.trello.model.UserReportDto;
import uz.anas.trello.repo.UserRepo;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<User> findAllByTaskId(UUID taskId) {
        return userRepo.findAllByTaskIdNot(taskId);
    }

    @Override
    public List<UserReportDto> getUsersReport() {
        return userRepo.findAllUsersReport();
    }

    @Override
    public boolean checkAdmin(User user) {
        return user.getUsername().equals("jason");
    }

    @Override
    public String saveNewUser(String firstName, String lastName, String username, String password, Model model) {
        if (userRepo.existsByUsername(username)) {
            model.addAttribute("error", "Username already taken");
            return "sign_up";
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        return "redirect:/login";
    }
}
