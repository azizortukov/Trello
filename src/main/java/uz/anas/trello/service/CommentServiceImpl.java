package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.repo.CommentRepo;

@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

}
