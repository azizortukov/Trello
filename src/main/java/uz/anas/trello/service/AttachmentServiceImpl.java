package uz.anas.trello.service;

import jakarta.servlet.http.HttpServletResponse;
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
        if (attachmentId != null) {
            attachmentRepo.deleteById(attachmentId);
        }
    }

    @SneakyThrows
    @Override
    public void saveAttachment(MultipartFile fileUpload, Task taskById) {
        if (fileUpload != null && !fileUpload.isEmpty()) {
            attachmentRepo.save(Attachment.builder()
                    .task(taskById)
                    .content(fileUpload.getBytes())
                    .fileName(fileUpload.getOriginalFilename())
                    .build());
        }
    }

    @Override
    public List<Attachment> findAllByTaskId(UUID taskId) {
        return attachmentRepo.findAllByTaskId(taskId);
    }

    @SneakyThrows
    @Override
    public void sendFileToResponse(UUID attachmentId, HttpServletResponse response) {
        Attachment attachment = attachmentRepo.findById(attachmentId).orElseThrow(RuntimeException::new);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getFileName());
        response.getOutputStream().write(attachment.getContent());
    }
}
