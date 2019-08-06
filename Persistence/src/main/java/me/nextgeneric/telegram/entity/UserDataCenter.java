package me.nextgeneric.telegram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataCenter {

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private int port;

}
