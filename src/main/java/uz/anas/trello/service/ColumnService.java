package uz.anas.trello.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.User;
import uz.anas.trello.model.ColumnReportDto;

import java.util.List;
import java.util.UUID;

@Service
public interface ColumnService {

    List<Column> findAllColumnsByUser(User user);

    int findLatestColumnNum();

    Column save(Column column);

    Column findById(UUID columnId);

    List<Column> findAll();

    List<ColumnReportDto> getColumnsReport();

    void moveColumnNext(UUID columnId);

    void moveColumnPrevious(UUID columnId);

    void buildAndSave(String columnName, Boolean finishLine);

    void archiveById(UUID columnId);

    void updateColumn(String columnName, HttpSession httpSession);
}
