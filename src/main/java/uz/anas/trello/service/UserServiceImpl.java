package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.User;
import uz.anas.trello.repo.UserRepo;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findById(UUID userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public List<User> findAllByTaskId(UUID taskId) {
        return userRepo.findAllByTaskIdNot(taskId);
    }
}
