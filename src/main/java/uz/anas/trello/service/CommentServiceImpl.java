package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.Comment;
import uz.anas.trello.entity.Task;
import uz.anas.trello.entity.User;
import uz.anas.trello.repo.CommentRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    @Override
    public void addCommentByTaskId(Task taskById, String comment, User user) {
        if (comment != null && !comment.isEmpty()) {
            commentRepo.save(Comment.builder()
                    .task(taskById)
                    .text(comment)
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build());
        }
    }

    @Override
    public List<Comment> findAllByTaskId(UUID taskId) {
        return commentRepo.findAllByTaskIdAndNotArchived(taskId);
    }

    @Override
    public void archiveById(UUID id) {
        commentRepo.findById(id).ifPresent(comment -> {
            comment.setArchived(true);
            commentRepo.save(comment);
        });
    }
}
