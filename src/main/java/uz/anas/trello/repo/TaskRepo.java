package uz.anas.trello.repo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.Task;

import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
            update columns_tasks set column_id =:columnId where tasks_id = :taskId""")
    void changeTaskColumn(UUID taskId, UUID columnId);
}
