package uz.anas.trello.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.anas.trello.entity.Attachment;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, UUID> {

    List<Attachment> findAllByTaskId(UUID taskId);

}
