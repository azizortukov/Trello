package uz.anas.trello.service;

import org.springframework.stereotype.Service;
import uz.anas.trello.entity.Task;

import java.util.UUID;

@Service
public interface TaskService {


    Task save(Task task);

    void changeTaskColumn(UUID taskId, UUID columnId);

    Task findTaskById(UUID taskId);

}
