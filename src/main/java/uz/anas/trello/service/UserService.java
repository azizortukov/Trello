package uz.anas.trello.service;

import org.springframework.stereotype.Service;
import uz.anas.trello.entity.User;

@Service
public interface UserService {


    User save(User user);
}
