package uz.anas.trello.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.ColumnServiceImpl;

import java.util.UUID;

@Controller
@RequestMapping("/column")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnServiceImpl columnService;

    @PostMapping("/add")
    public String addColumn(@RequestParam String columnName, @RequestParam(defaultValue = "false") Boolean finishLine) {
        columnService.buildAndSave(columnName, finishLine);
        return "redirect:/";
    }

    @GetMapping("/move/next")
    public String moveColumnToNext(@RequestParam UUID columnId, @AuthenticationPrincipal User user) {
        if (!user.getUsername().equals("jason")) {
            return "redirect:/";
        }
        columnService.moveColumnNext(columnId);
        return "redirect:/";
    }

    @GetMapping("/move/previous")
    public String moveColumnToPrevious(@RequestParam UUID columnId, @AuthenticationPrincipal User user) {
        if (!user.getUsername().equals("jason")) {
            return "redirect:/";
        }
        columnService.moveColumnPrevious(columnId);
        return "redirect:/";
    }

    @PostMapping("/move/archive")
    public String moveColumnToArchive(@RequestParam UUID columnId, @AuthenticationPrincipal User user) {
        if (!user.getUsername().equals("jason")) {
            return "redirect:/";
        }
        columnService.archiveById(columnId);
        return "redirect:/";
    }

}
