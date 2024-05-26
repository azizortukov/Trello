package uz.anas.trello.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.Task;
import uz.anas.trello.service.ColumnServiceImpl;
import uz.anas.trello.service.TaskServiceImpl;

import java.util.UUID;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final ColumnServiceImpl columnService;
    private final TaskServiceImpl taskService;

    @PostMapping("/add")
    public String addTask(@RequestParam String taskName,
                          @RequestParam UUID columnId) {
        Column column = columnService.findById(columnId);
        column.getTasks().add(taskService.save(Task.builder()
                .name(taskName)
                .build()));
        columnService.save(column);
        return "redirect:/";
    }

    @PostMapping("/move")
    public String moveTaskToNext(@RequestParam String task) {
        String[] split = task.split("/");
        UUID taskId = UUID.fromString(split[0]);
        UUID columnId = UUID.fromString(split[1]);
        taskService.changeTaskColumn(taskId, columnId);
        return "redirect:/";
    }

}
