package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.repo.ColumnRepo;

@Component
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepo columnRepo;

}
