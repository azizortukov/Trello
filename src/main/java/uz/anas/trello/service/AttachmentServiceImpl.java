package uz.anas.trello.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.anas.trello.entity.Attachment;
import uz.anas.trello.entity.Task;
import uz.anas.trello.repo.AttachmentRepo;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepo attachmentRepo;

    @Override
    public void removeById(UUID attachmentId) {
        attachmentRepo.deleteById(attachmentId);
    }

    @SneakyThrows
    @Override
    public void saveAttachment(MultipartFile fileUpload, Task taskById) {
        attachmentRepo.save(Attachment.builder()
                .task(taskById)
                .content(fileUpload.getBytes())
                .fileName(fileUpload.getOriginalFilename())
                .build());
    }

    @Override
    public List<Attachment> findAllByTaskId(UUID taskId) {
        return attachmentRepo.findAllByTaskId(taskId);
    }

    @Override
    public Attachment findById(UUID attachmentId) {
        return attachmentRepo.findById(attachmentId).orElse(null);
    }
}
