package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.Column;
import uz.anas.trello.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColumnRepo extends JpaRepository<Column, UUID> {

    List<Column> findAllByOwnerOrderByColumnOrder(User owner);


    @Query(nativeQuery = true, value = """
            select max(column_order) from columns where owner_id = :userId""")
    Integer findUserLatestColumnNum(UUID userId);

    Column findByColumnOrder(Integer columnOrder);

}
