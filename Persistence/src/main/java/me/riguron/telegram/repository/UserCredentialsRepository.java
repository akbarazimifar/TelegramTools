package me.riguron.telegram.repository;

import me.riguron.telegram.entity.UserCredentials;
import me.riguron.telegram.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String> {
}
