package uz.anas.trello.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Attachment extends BaseEntity {

    private byte[] content;
    private String fileName;
    @ManyToOne
    private Task task;
}
