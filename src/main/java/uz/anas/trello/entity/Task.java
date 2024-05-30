package uz.anas.trello.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Task extends BaseEntity {

    private String name;
    private LocalDateTime deadline;
    private boolean finished;
    private boolean lateFinished;
    @ManyToMany
    @ToString.Exclude
    private List<User> members;
    private boolean isArchived;

}
