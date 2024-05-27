package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.Task;
import uz.anas.trello.repo.TaskRepo;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Override
    public Task save(Task task) {
        return taskRepo.save(task);
    }

    @Override
    public void changeTaskColumn(UUID taskId, UUID columnId) {
        taskRepo.changeTaskColumn(taskId, columnId);
    }

    @Override
    public Task findTaskById(UUID taskId) {
        return taskRepo.findById(taskId).orElse(null);
    }

}
