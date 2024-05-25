package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.User;
import uz.anas.trello.repo.UserRepo;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }
}
