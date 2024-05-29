package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.Task;
import uz.anas.trello.repo.ColumnRepo;
import uz.anas.trello.repo.TaskRepo;
import uz.anas.trello.repo.UserRepo;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final ColumnRepo columnRepo;
    private final UserRepo userRepo;

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

    @Override
    public void addTaskToColumn(String taskName, UUID columnId) {
        if (taskName != null && !taskName.isEmpty() && columnId != null) {
            Column column = columnRepo.findById(columnId).orElseThrow(RuntimeException::new);
            Task savedTask = taskRepo.save(Task.builder()
                    .name(taskName)
                    .build());
            column.getTasks().add(savedTask);
            columnRepo.save(column);
        }
    }

    @Override
    public void moveTask(String task) {
        String[] split = task.split("/");
        Task taskById = taskRepo.findById(UUID.fromString(split[0])).orElseThrow(RuntimeException::new);
        Column columnById = columnRepo.findById(UUID.fromString(split[1])).orElseThrow(RuntimeException::new);
        if (columnById.isFinishLine() || columnById.getColumnOrder() > getFinishColNum()) {
            if (taskById.getDeadline() != null && taskById.getDeadline().isBefore(LocalDateTime.now())) {
                taskById.setLateFinished(true);
            }
            taskById.setFinished(true);
            taskRepo.save(taskById);
        }else {
            taskById.setFinished(false);
            taskById.setLateFinished(false);
            taskRepo.save(taskById);
        }
        //Change tasks column id reference
        changeTaskColumn(taskById.getId(), columnById.getId());
    }

    @Override
    public int getFinishColNum() {
        return columnRepo.getFinishColNum().orElse(0);
    }

    @Override
    public void archiveById(UUID id) {
        Task task = taskRepo.findById(id).orElseThrow(RuntimeException::new);
        task.setArchived(true);
        taskRepo.save(task);
    }

    @Override
    public void updateTask(String taskName, UUID addUserId, UUID removeUserId, LocalDateTime taskDeadLine, Task taskById) {
        if (taskName != null && !taskName.isEmpty()) {
            taskById.setName(taskName);
        }
        if (addUserId != null) {
            taskById.getMembers().add(userRepo.findById(addUserId).orElse(null));
        }
        if (removeUserId != null) {
            taskById.getMembers().remove(userRepo.findById(removeUserId).orElse(null));
        }
        if (taskDeadLine != null) {
            taskById.setLateFinished(taskDeadLine.isBefore(LocalDateTime.now()) && taskById.isFinished());
            taskById.setDeadline(taskDeadLine);
        }
        taskRepo.save(taskById);
    }

}
