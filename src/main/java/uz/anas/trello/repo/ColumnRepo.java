package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.Column;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColumnRepo extends JpaRepository<Column, UUID> {

    @Query(nativeQuery = true, value = """
            select c.* from columns c
            left join public.columns_tasks ct on c.id = ct.column_id
            left join task_members tm on tm.task_id = ct.tasks_id
            where c.owner_id =:userId or tm.members_id =:userId
            group by c.id, c.column_order
            order by c.column_order""")
    List<Column> findAllColumnsByUserId(UUID userId);


    @Query(nativeQuery = true, value = """
            select max(column_order) from columns""")
    Integer findLatestColumnNum();

    Column findByColumnOrder(Integer columnOrder);

    List<Column> findAllByOrderByColumnOrder();

}
