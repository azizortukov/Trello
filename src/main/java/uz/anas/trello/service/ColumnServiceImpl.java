package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.User;
import uz.anas.trello.repo.ColumnRepo;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepo columnRepo;

    @Override
    public List<Column> findAllColumnsByUser(User user) {
        return columnRepo.findAllColumnsByUserId(user.getId());
    }

    @Override
    public int findLatestColumnNum() {
        Integer lastColumn = columnRepo.findLatestColumnNum();
        return lastColumn == null ? 1 : lastColumn;
    }

    @Override
    public Column save(Column column) {
        return columnRepo.save(column);
    }

    @Override
    public Column findById(UUID columnId) {
        return columnRepo.findById(columnId).orElse(null);
    }

    @Override
    public List<Column> findAll() {
        return columnRepo.findAllByOrderByColumnOrder();
    }

    @Override
    public Column findByOrder(int desiredOrder) {
        return columnRepo.findByColumnOrder(desiredOrder);
    }



}
