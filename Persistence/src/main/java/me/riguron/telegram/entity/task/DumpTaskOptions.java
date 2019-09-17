package me.riguron.telegram.entity.task;

import lombok.*;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.DocumentType;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Immutable
@Entity
@Data
@Setter(NONE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DumpTaskOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type;

    @Column(nullable = false)
    private boolean images;

    public DumpTaskOptions(String channel, DocumentType type, boolean images) {
        this.channel = channel;
        this.type = type;
        this.images = images;
    }


}
