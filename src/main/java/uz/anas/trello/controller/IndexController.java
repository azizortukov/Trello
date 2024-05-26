package uz.anas.trello.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.ColumnServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ColumnServiceImpl columnService;

    @GetMapping
    public String index(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Column> columns = columnService.findAllColumnsByUser(user);
        model.addAttribute("columns", columns);
        return "index";
    }

}
