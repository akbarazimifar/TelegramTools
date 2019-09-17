package me.riguron.telegram.auth;

import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.repository.UserProfileRepository;
import me.riguron.telegram.user.UserSession;
import me.riguron.telegram.validate.PhoneNumberFormatter;
import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.entity.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DatabaseUserDetailsServiceTest {

    private static final String LOGIN = "login";

    private static final String ACCESS_KEY = "key";

    private final UserSession userSession = mock(UserSession.class);

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final UserProfileRepository userProfileRepository = mock(UserProfileRepository.class);

    private final PhoneNumberFormatter phoneNumberFormatter = mock(PhoneNumberFormatter.class);

    private DatabaseUserDetailsService userDetailsService;

    @Before
    public void constructTestService() {
        this.userDetailsService = new DatabaseUserDetailsService(
                phoneNumberFormatter, userSession,
                passwordEncoder,
                userProfileRepository,
                ACCESS_KEY
        );
    }

    @Test
    public void encryptAccessKey() {
        when(passwordEncoder.encode(eq(ACCESS_KEY))).thenReturn("encodedKey");
        userDetailsService.encryptAccessKey();
        assertEquals("encodedKey", ReflectionTestUtils.getField(userDetailsService, "accessKey"));
    }

    @Test
    public void whenUserExistsThenLoadedSuccessfullyAndRoleIsAssigned() {

        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getId()).thenReturn(10);
        when(userProfile.getRole()).thenReturn("role");

        genericRoleTest(Optional.of(userProfile), "role", () -> verify(userSession).setUserId(10));

    }

    @Test
    public void whenUserDoesNotExistThenUnconfirmedRoleAssigned() {
        genericRoleTest(Optional.empty(), SystemRoles.ROLE_UNCONFIRMED);
    }

    private void genericRoleTest(Optional<UserProfile> returnedProfile, String expectedRole, Runnable... additionalAssertions) {

        when(phoneNumberFormatter.transform(eq(LOGIN))).thenReturn(LOGIN);
        when(userProfileRepository.findByUsername(eq(LOGIN))).thenReturn(returnedProfile);

        UserDetails userDetails = userDetailsService.loadUserByUsername(LOGIN);

        for (Runnable additionalAssertion : additionalAssertions) {
            additionalAssertion.run();
        }

        assertEquals("key", userDetails.getPassword());
        assertEquals(LOGIN, userDetails.getUsername());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals(expectedRole, authorities.iterator().next().getAuthority());
    }
}