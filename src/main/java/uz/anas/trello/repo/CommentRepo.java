package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.Comment;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepo extends JpaRepository<Comment, UUID> {

    @Query(nativeQuery = true, value = """
            select * from comment where is_archived = false and task_id =:taskId order by created_at desc""")
    List<Comment> findAllByTaskIdAndNotArchived(UUID taskId);

}
