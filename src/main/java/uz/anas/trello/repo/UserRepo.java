package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.User;

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

}
