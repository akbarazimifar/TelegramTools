package me.riguron.telegram.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.riguron.telegram.entity.task.DumpTaskRecord;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.NONE;

@Table(indexes = {
        @Index(name = "phone_index", columnList = "phone")
})
@Immutable
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Setter(NONE)
public class UserProfile implements Persistable<Integer> {

    @Id
    @Column(updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, length = 100)
    @EqualsAndHashCode.Include
    private String name;

    @Column(unique = true, nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    @NaturalId
    private String phone;

    @Column(nullable = false)
    private String role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
    @JoinColumn(name = "credentials_id", nullable = false, unique = true)
    private UserCredentials userCredentials;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name = "channel", updatable = false, length = 20)
    @CollectionTable(name = "last_messages", joinColumns = {
            @JoinColumn(name = "profile_id")
    })
    private Map<String, Integer> lastMessages = new HashMap<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userProfile")
    private List<DumpTaskRecord> records = new ArrayList<>();


    public UserProfile(int id, String name, String phone, String role, UserCredentials userCredentials) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.userCredentials = userCredentials;
        this.role = role;
    }

    public int getLastMessageId(String channelName) {
        return lastMessages.getOrDefault(channelName, 0);
    }

    public void setLastMessageId(String channelName, int id) {
      lastMessages.put(channelName, id);
    }

    public void addRecord(DumpTaskRecord dumpTaskRecord) {
        this.getRecords().add(dumpTaskRecord);
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
