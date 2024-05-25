package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.repo.TaskRepo;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

}
