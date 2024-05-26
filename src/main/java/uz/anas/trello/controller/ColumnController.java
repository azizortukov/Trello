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

import java.util.UUID;

@Controller
@RequestMapping("/column")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnServiceImpl columnService;

    @PostMapping("/add")
    public String addColumn(@RequestParam String columnName, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        int latestColOrder = columnService.findUserLatestColumnNum(user);
        columnService.save(Column.builder()
                .columnOrder(latestColOrder + 1)
                .name(columnName)
                .owner(user)
                .build());
        return "redirect:/";
    }

    @GetMapping("/move/next")
    public String moveColumnToNext(@RequestParam UUID columnId) {
        Column column = columnService.findById(columnId);
        int desiredOrder = column.getColumnOrder() + 1;
        Column columnByOrder = columnService.findByOrder(desiredOrder);
        columnByOrder.setColumnOrder(desiredOrder - 1);
        column.setColumnOrder(desiredOrder);
        columnService.save(column);
        columnService.save(columnByOrder);
        return "redirect:/";
    }


    @GetMapping("/move/previous")
    public String moveColumnToPrevious(@RequestParam UUID columnId) {
        Column column = columnService.findById(columnId);
        int desiredOrder = column.getColumnOrder() - 1;
        Column columnByOrder = columnService.findByOrder(desiredOrder);
        columnByOrder.setColumnOrder(desiredOrder + 1);
        column.setColumnOrder(desiredOrder);
        columnService.save(column);
        columnService.save(columnByOrder);
        return "redirect:/";
    }

}
