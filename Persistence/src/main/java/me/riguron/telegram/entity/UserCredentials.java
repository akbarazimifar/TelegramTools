package me.riguron.telegram.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "tg_credentials")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserCredentials implements Persistable<String> {

    @Id
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private String phone;

    @Lob
    @Column(name = "authKey", nullable = false)
    @ToString.Exclude
    private byte[] authKey;

    @Column()
    private UserDataCenter dataCenter;

    public UserCredentials(String phone, byte[] authKey, UserDataCenter dataCenter) {
        this.phone = phone;
        this.authKey = authKey;
        this.dataCenter = dataCenter;
    }

    public void refresh(UserCredentials existingCredentials) {
        this.phone = existingCredentials.getPhone();
        this.authKey = existingCredentials.getAuthKey();
        this.dataCenter = existingCredentials.getDataCenter();
    }

    @Override
    public String getId() {
        return phone;
    }

    @Override
    public boolean isNew() {
        return false;
    }
}