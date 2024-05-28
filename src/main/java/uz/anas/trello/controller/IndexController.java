package uz.anas.trello.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.ColumnServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ColumnServiceImpl columnService;

    @GetMapping
    public String index(Model model,
                        @AuthenticationPrincipal User user,
                        @RequestParam(required = false) boolean filtered) {
        List<Column> columns = filtered ? columnService.findAllColumnsByUser(user) : columnService.findAll();
        if (filtered) {
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("columns", columns);
        model.addAttribute("filtered", filtered);
        return "index";
    }


}
