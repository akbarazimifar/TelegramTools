package me.riguron.telegram.user;

import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.repository.UserProfileRepository;
import me.riguron.telegram.entity.UserProfile;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UserService {

    private final UserProfileRepository userProfileRepository;

    public UserService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional(readOnly = true)
    public Optional<UserProfile> findByUsername(String phone) {
        return userProfileRepository.findByUsername(phone);
    }
}
