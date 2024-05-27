package uz.anas.trello.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.anas.trello.entity.Column;
import uz.anas.trello.service.ColumnServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ColumnServiceImpl columnService;

    @GetMapping
    public String index(Model model) {
        List<Column> columns = columnService.findAll();
        model.addAttribute("columns", columns);
        return "index";
    }

}
