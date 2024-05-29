package uz.anas.trello.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.anas.trello.entity.Task;
import uz.anas.trello.entity.User;
import uz.anas.trello.service.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final ColumnServiceImpl columnService;
    private final TaskServiceImpl taskService;
    private final HttpSession httpSession;
    private final UserServiceImpl userService;
    private final CommentServiceImpl commentService;
    private final AttachmentServiceImpl attachmentService;

    @PostMapping("/add")
    public String addTask(@RequestParam String taskName, @RequestParam UUID columnId, @AuthenticationPrincipal User user) {
        if (userService.checkAdmin(user)) {
            taskService.addTaskToColumn(taskName, columnId);
        }
        return "redirect:/";
    }

    @PostMapping("/move")
    public String moveTask(@RequestParam String task, @AuthenticationPrincipal User user) {
        if (userService.checkAdmin(user)) {
            taskService.moveTask(task);
        }
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal User user,
                       @RequestParam(required = false) String columnName, @RequestParam(required = false) String taskName,
                       @RequestParam(required = false) UUID addUserId, @RequestParam(required = false) UUID removeUserId,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime taskDeadLine,
                       @RequestParam(required = false) MultipartFile fileUpload, @RequestParam(required = false) UUID attachmentId,
                       @RequestParam(required = false) String comment
    ) {
        Task taskById = taskService.findTaskById((UUID) httpSession.getAttribute("taskId"));
        commentService.addCommentByTaskId(taskById, comment, user);
        attachmentService.saveAttachment(fileUpload, taskById);
        attachmentService.removeById(attachmentId);
        if (userService.checkAdmin(user)) {
            columnService.updateColumn(columnName, httpSession);
            taskService.updateTask(taskName, addUserId, removeUserId, taskDeadLine, taskById);
        }
        return "redirect:/task/conf";
    }

    @GetMapping("/conf")
    public String config(Model model, @RequestParam(required = false) String ids) {
        UUID columnId = (UUID) httpSession.getAttribute("columnId");
        UUID taskId = (UUID) httpSession.getAttribute("taskId");
        if (ids != null) {
            String[] split = ids.split("/");
            columnId = UUID.fromString(split[0]);
            taskId = UUID.fromString(split[1]);
        }
        model.addAttribute("column", columnService.findById(columnId));
        model.addAttribute("task", taskService.findTaskById(taskId));
        model.addAttribute("users", userService.findAllByTaskId(taskId));
        model.addAttribute("comments", commentService.findAllByTaskId(taskId));
        model.addAttribute("files", attachmentService.findAllByTaskId(taskId));
        httpSession.setAttribute("columnId", columnId);
        httpSession.setAttribute("taskId", taskId);
        return "task_config";
    }

    @GetMapping("/file/download")
    public void fileDownload(@RequestParam UUID attachmentId, HttpServletResponse response) {
        attachmentService.sendFileToResponse(attachmentId, response);
    }

    @GetMapping("/archive/{taskId}")
    public String archiveTask(@PathVariable UUID taskId, @AuthenticationPrincipal User user) {
        if (userService.checkAdmin(user)) {
            taskService.archiveById(taskId);
        }
        return "redirect:/";
    }

    @GetMapping("/archive/comment/{commentId}")
    public String archiveComment(@PathVariable UUID commentId) {
        commentService.archiveById(commentId);
        return "redirect:/task/conf";
    }

}
