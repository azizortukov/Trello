package uz.anas.trello.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.anas.trello.entity.User;
import uz.anas.trello.model.UserReportDto;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {


    User save(User user);

    List<User> findAll();

    List<User> findAllByTaskId(UUID taskId);

    List<UserReportDto> getUsersReport();

    boolean checkAdmin(User user);

    String saveNewUser(String firstName, String lastName, String username, String password, Model model);
}
