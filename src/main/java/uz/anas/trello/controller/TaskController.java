package uz.anas.trello.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.anas.trello.entity.Attachment;
import uz.anas.trello.entity.Column;
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
    public String addTask(@RequestParam String taskName,
                          @RequestParam UUID columnId) {
        if (taskName != null && !taskName.isEmpty() && columnId != null) {
            Column column = columnService.findById(columnId);
            column.getTasks().add(taskService.save(Task.builder()
                    .name(taskName)
                    .build()));
            columnService.save(column);
        }
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

    @SneakyThrows
    @PostMapping("/edit")
    public String edit(@AuthenticationPrincipal User user,
                       @RequestParam(required = false) String columnName,
                       @RequestParam(required = false) String taskName,
                       @RequestParam(required = false) UUID addUserId,
                       @RequestParam(required = false) UUID removeUserId,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                       LocalDateTime taskDeadLine,
                       @RequestParam(required = false) MultipartFile fileUpload,
                       @RequestParam(required = false) UUID attachmentId,
                       @RequestParam(required = false) String comment
    ) {
        Column column = columnService.findById((UUID) httpSession.getAttribute("columnId"));
        Task taskById = taskService.findTaskById((UUID) httpSession.getAttribute("taskId"));
        if (columnName != null && !columnName.isEmpty()) {
            column.setName(columnName);
            columnService.save(column);
        }
        if (taskName != null && !taskName.isEmpty()) {
            taskById.setName(taskName);
        }
        if (addUserId != null) {
            taskById.getMembers().add(userService.findById(addUserId));
        }
        if (removeUserId != null) {
            taskById.getMembers().remove(userService.findById(removeUserId));
        }
        if (taskDeadLine != null) {
            taskById.setDeadline(taskDeadLine);
        }
        if (comment != null && !comment.isEmpty()) {
            commentService.addCommentByTaskId(taskById, comment, user);
        }
        if (fileUpload != null && !fileUpload.isEmpty()) {
            attachmentService.saveAttachment(fileUpload, taskById);
        }
        if (attachmentId != null) {
            attachmentService.removeById(attachmentId);
        }
        taskService.save(taskById);
        return "redirect:/task/conf";
    }

    @SneakyThrows
    @GetMapping("/file/download")
    public void fileDownload(@RequestParam UUID attachmentId, HttpServletResponse response) {
        Attachment attachment = attachmentService.findById(attachmentId);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getFileName());
        response.getOutputStream().write(attachment.getContent());
    }

}
