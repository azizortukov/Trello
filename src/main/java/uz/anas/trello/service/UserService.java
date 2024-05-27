package uz.anas.trello.service;

import org.springframework.stereotype.Service;
import uz.anas.trello.entity.User;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {


    User save(User user);

    List<User> findAll();

    User findById(UUID userId);

    List<User> findAllByTaskId(UUID taskId);
}
