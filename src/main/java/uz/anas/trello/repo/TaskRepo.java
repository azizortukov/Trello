package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.Task;

import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {



}
