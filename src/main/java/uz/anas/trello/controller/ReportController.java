package uz.anas.trello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.anas.trello.service.ColumnServiceImpl;
import uz.anas.trello.service.UserServiceImpl;

@Controller
@RequestMapping("/report")
public class ReportController {


    private final UserServiceImpl userServiceImpl;
    private final ColumnServiceImpl columnServiceImpl;

    public ReportController(UserServiceImpl userServiceImpl, ColumnServiceImpl columnServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.columnServiceImpl = columnServiceImpl;
    }

    @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("users", userServiceImpl.getUsersReport());
        return "report_members";
    }

    @GetMapping("/columns")
    public String columns(Model model) {
        model.addAttribute("columns", columnServiceImpl.getColumnsReport());
        return "report_columns";
    }

}
