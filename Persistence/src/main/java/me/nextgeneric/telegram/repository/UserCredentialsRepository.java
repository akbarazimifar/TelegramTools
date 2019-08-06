package me.nextgeneric.telegram.repository;

import me.nextgeneric.telegram.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String> {
}
