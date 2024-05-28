package uz.anas.trello.service;

import org.springframework.stereotype.Service;
import uz.anas.trello.entity.Comment;
import uz.anas.trello.entity.Task;
import uz.anas.trello.entity.User;

import java.util.List;
import java.util.UUID;

@Service
public interface CommentService {


    void addCommentByTaskId(Task taskById, String comment, User user);

    List<Comment> findAllByTaskId(UUID taskId);

    void archiveById(UUID id);
}
