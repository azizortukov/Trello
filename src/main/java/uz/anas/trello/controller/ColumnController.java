package uz.anas.trello.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.ColumnServiceImpl;
import uz.anas.trello.service.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/column")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnServiceImpl columnService;
    private final TaskServiceImpl taskServiceImpl;

    @PostMapping("/add")
    public String addColumn(@RequestParam String columnName, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (columnName != null && !columnName.isEmpty()) {
            int latestColOrder = columnService.findLatestColumnNum();
            columnService.save(Column.builder()
                    .columnOrder(latestColOrder + 1)
                    .name(columnName)
                    .owner(user)
                    .build());
        }
        return "redirect:/";
    }

    @GetMapping("/move/next")
    public String moveColumnToNext(@RequestParam UUID columnId) {
        Column column = columnService.findById(columnId);
        int desiredOrder = column.getColumnOrder() + 1;
        Column columnByOrder = columnService.findByOrder(desiredOrder);
        columnByOrder.setColumnOrder(desiredOrder - 1);
        column.setColumnOrder(desiredOrder);
        //Check if the setting column will be finish column, then set the tasks as finished
        int latestColumnNum = columnService.findLatestColumnNum();
        if (latestColumnNum == column.getColumnOrder()) {
            column.getTasks().forEach(task -> {
                if (task.getDeadline() != null && task.getDeadline().isBefore(LocalDateTime.now())) {
                    task.setLateFinished(true);
                }
                task.setFinished(true);
                taskServiceImpl.save(task);
            });
        }

        columnService.save(column);
        columnService.save(columnByOrder);
        return "redirect:/";
    }


    @GetMapping("/move/previous")
    public String moveColumnToPrevious(@RequestParam UUID columnId) {
        Column column = columnService.findById(columnId);
        //Check if the setting column was the finish column, if it was, then set the tasks as not finished
        int latestColumnNum = columnService.findLatestColumnNum();
        if (latestColumnNum == column.getColumnOrder()) {
            column.getTasks().forEach(task -> {
                task.setLateFinished(false);
                task.setFinished(false);
                taskServiceImpl.save(task);
            });
        }

        int desiredOrder = column.getColumnOrder() - 1;
        Column columnByOrder = columnService.findByOrder(desiredOrder);
        columnByOrder.setColumnOrder(desiredOrder + 1);
        column.setColumnOrder(desiredOrder);




        columnService.save(column);
        columnService.save(columnByOrder);
        return "redirect:/";
    }

}
