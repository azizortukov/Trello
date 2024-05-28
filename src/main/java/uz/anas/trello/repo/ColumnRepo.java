package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.Column;
import uz.anas.trello.model.ColumnReportDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ColumnRepo extends JpaRepository<Column, UUID> {

    @Query(nativeQuery = true, value = """
            select c.* from columns c
            left join columns_tasks ct on c.id = ct.column_id
            left join task_members tm on tm.task_id = ct.tasks_id
            where tm.members_id =:userId and c.is_archived = false
            group by c.id, c.column_order
            order by c.column_order""")
    List<Column> findAllColumnsByUserId(UUID userId);


    @Query(nativeQuery = true, value = """
            select max(column_order) from columns where is_archived = false""")
    Integer findLatestColumnNum();

    @Query(nativeQuery = true, value = """
            select c.* from columns c
            where c.is_archived = false and c.column_order >= :columnOrder
            order by c.column_order limit 1""")
    Column findByColumnOrder(Integer columnOrder);

    @Query(nativeQuery = true, value = """
            select distinct c.* from columns c
            left join columns_tasks ct on c.id = ct.column_id
            left join task t on ct.tasks_id = t.id and t.is_archived = false
            where c.is_archived = false and (t.id is null or t.id is not null)
            order by c.column_order""")
    List<Column> findAllByOrderByColumnOrder();

    @Query(nativeQuery = true, value = """
            select c.name as name, count(ct.tasks_id) from columns c
            left join columns_tasks ct on c.id = ct.column_id
            where c.is_archived = false
            group by c.id, c.column_order
            order by c.column_order""")
    List<ColumnReportDto> getColumnsReport();

    @Query(nativeQuery = true, value = """
            select column_order from columns where is_archived = false and finish_line = true limit 1""")
    Optional<Integer> getFinishColNum();
}
