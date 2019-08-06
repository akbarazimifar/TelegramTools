package me.nextgeneric.telegram.user;

import me.nextgeneric.telegram.entity.UserProfile;
import me.nextgeneric.telegram.repository.UserProfileRepository;
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
