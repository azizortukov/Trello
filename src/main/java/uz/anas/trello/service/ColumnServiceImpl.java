package uz.anas.trello.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.anas.trello.component.Runner;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.Task;
import uz.anas.trello.entity.User;
import uz.anas.trello.model.ColumnReportDto;
import uz.anas.trello.repo.ColumnRepo;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepo columnRepo;
    private final Runner runner;

    @Override
    public List<Column> findAllColumnsByUser(User user) {
        List<Column> columns = columnRepo.findAllColumnsByUserId(user.getId());
        columns.forEach(column -> {
            List <Task> userTasks = column.getTasks().stream()
                    .filter(task -> runner.checkTaskMembers(task.getMembers(), user.getId())).toList();
            column.setTasks(userTasks);
        });
        return columns;
    }

    @Override
    public int findLatestColumnNum() {
        Integer lastColumn = columnRepo.findLatestColumnNum();
        return lastColumn == null ? 0 : lastColumn;
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
    public List<ColumnReportDto> getColumnsReport() {
        return columnRepo.getColumnsReport();
    }

    @Override
    public void moveColumnNext(UUID columnId) {
        Column column = columnRepo.findById(columnId).orElseThrow(RuntimeException::new);
        int desiredOrder = column.getColumnOrder() + 1;
        Column nextColumn = columnRepo.findByColumnOrder(desiredOrder);
        nextColumn.setColumnOrder(desiredOrder - 1);
        column.setColumnOrder(desiredOrder);
        columnRepo.save(column);
        columnRepo.save(nextColumn);
    }

    @Override
    public void moveColumnPrevious(UUID columnId) {
        Column column = columnRepo.findById(columnId).orElseThrow(RuntimeException::new);
        int desiredOrder = column.getColumnOrder() - 1;
        Column previousColumn = columnRepo.findByColumnOrder(desiredOrder);
        previousColumn.setColumnOrder(desiredOrder + 1);
        column.setColumnOrder(desiredOrder);
        columnRepo.save(column);
        columnRepo.save(previousColumn);
    }

    @Override
    public void buildAndSave(String columnName, Boolean finishLine) {
        if (columnName != null && !columnName.isEmpty()) {
            int latestColOrder = findLatestColumnNum();
            columnRepo.save(Column.builder()
                    .columnOrder(latestColOrder + 1)
                    .finishLine(finishLine)
                    .name(columnName)
                    .build());
        }
    }

    @Override
    public void archiveById(UUID columnId) {
        Column column = columnRepo.findById(columnId).orElseThrow(RuntimeException::new);
        column.setArchived(true);
        columnRepo.save(column);
    }

    @Override
    public void updateColumn(String columnName, HttpSession httpSession) {
        if (columnName != null && !columnName.isEmpty()) {
            Column column = columnRepo.findById((UUID) httpSession.getAttribute("columnId")).orElseThrow(RuntimeException::new);
            column.setName(columnName);
            columnRepo.save(column);
        }
    }

}
