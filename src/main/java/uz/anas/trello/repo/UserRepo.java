package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.User;
import uz.anas.trello.model.UserReportDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = """
            select u.* from users u
            where u.id not in (
                select tm.members_id from task_members tm
                where tm.task_id = :taskId
            )""")
    List<User> findAllByTaskIdNot(UUID taskId);


    @Query(nativeQuery = true, value = """
            select u.first_name || ' ' || u.last_name as fullName,
            sum(case when t.finished = true and t.late_finished = false then 1 else 0 end) as onTimeTasks,
            sum(case when t.finished = true and t.late_finished = true then 1 else 0 end) as lateTasks,
            count(t.id) as finishedTasks
            from users u
            left join task_members tm on u.id = tm.members_id
            left join public.task t on t.id = tm.task_id and t.finished = true
            group by u.id""")
    List<UserReportDto> findAllUsersReport();
}
