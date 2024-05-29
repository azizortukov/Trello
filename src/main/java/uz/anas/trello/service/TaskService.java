package uz.anas.trello.service;

import org.springframework.stereotype.Service;
import uz.anas.trello.entity.Task;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public interface TaskService {


    Task save(Task task);

    void changeTaskColumn(UUID taskId, UUID columnId);

    Task findTaskById(UUID taskId);

    void addTaskToColumn(String taskName, UUID columnId);

    void moveTask(String task);

    int getFinishColNum();

    void archiveById(UUID id);

    void updateTask(String taskName, UUID addUserId, UUID removeUserId, LocalDateTime taskDeadLine, Task taskById);
}
