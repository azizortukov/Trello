package uz.anas.trello.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Comment extends BaseEntity {

    private String text;
    private LocalDateTime createdAt;
    @ManyToOne
    private User user;
    @ManyToOne
    private Task task;
    private boolean isArchived;

}
