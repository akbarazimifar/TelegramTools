package me.riguron.telegram.entity.task;

import lombok.*;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.entity.UserProfile;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Immutable
@Entity
@Data
@Setter(NONE)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DumpTaskRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true, updatable = false, length = 128)
    @NaturalId
    @EqualsAndHashCode.Include
    private String file;

    @Column(nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private long date;

    @Column(nullable = false, updatable = false)
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ToString.Exclude
    private UserProfile userProfile;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", updatable = false, unique = true)
    private DumpTaskState dumpTaskState;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "options_id", updatable = false, unique = true)
    private DumpTaskOptions dumpTaskOptions;

    public DumpTaskRecord(String file, boolean completed, DumpTaskState dumpTaskState, DumpTaskOptions dumpTaskOptions, UserProfile userProfile) {
        this.file = file;
        this.completed = completed;
        this.dumpTaskState = dumpTaskState;
        this.dumpTaskOptions = dumpTaskOptions;
        this.userProfile = userProfile;
    }

    @PrePersist
    public void setCreationTimestamp() {
        this.date = System.currentTimeMillis();
    }

}
