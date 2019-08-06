package me.nextgeneric.telegram.user;

import me.nextgeneric.telegram.entity.UserProfile;
import me.nextgeneric.telegram.repository.UserProfileRepository;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Test
    public void whenFindByUsernameThenFound() {
        UserProfileRepository userProfileRepository = mock(UserProfileRepository.class);
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getId()).thenReturn(1);

        Optional<UserProfile> returnOptional = Optional.of(userProfile);
        when(userProfileRepository.findByUsername(eq("phone"))).thenReturn(returnOptional);
        UserService userService = new UserService(userProfileRepository);
        Optional<UserProfile> returnedResult = userService.findByUsername("phone");

        assertTrue(returnedResult.isPresent());
        assertEquals(1, (int) returnedResult.get().getId());
    }

}