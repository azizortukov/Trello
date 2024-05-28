package uz.anas.trello.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
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
    private List<Task> tasks;
    private boolean isArchived;
}
