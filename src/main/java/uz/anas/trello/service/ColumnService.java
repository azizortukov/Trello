package uz.anas.trello.service;

import org.springframework.stereotype.Service;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.User;

import java.util.List;
import java.util.UUID;

@Service
public interface ColumnService {

    List<Column> findAllColumnsByUser(User user);


    int findLatestColumnNum();

    Column save(Column column);

    Column findById(UUID columnId);

    List<Column> findAll();

    Column findByOrder(int desiredOrder);
}
