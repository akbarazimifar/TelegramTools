package me.nextgeneric.telegram.entity.task;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

import static lombok.AccessLevel.NONE;

@Immutable
@Entity
@Data
@Setter(NONE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class DumpTaskState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String description;

    public DumpTaskState(String description) {
        this.description = description;
    }


}
