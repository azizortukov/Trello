package uz.anas.trello.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "columns")
public class Column extends BaseEntity {

    private String name;
    private Integer columnOrder;
    private boolean finishLine;
    @OneToMany
    @ToString.Exclude
    private List<Task> tasks;
    private boolean isArchived;
}
