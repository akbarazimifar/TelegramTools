package me.nextgeneric.telegram.auth;

import me.nextgeneric.telegram.SystemRoles;
import me.nextgeneric.telegram.repository.UserProfileRepository;
import me.nextgeneric.telegram.user.UserSession;
import me.nextgeneric.telegram.validate.PhoneNumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
@Primary
public class DatabaseUserDetailsService implements UserDetailsService {

    private final PhoneNumberFormatter phoneNumberFormatter;

    private final UserSession userSession;

    private final PasswordEncoder passwordEncoder;

    private final UserProfileRepository userProfileRepository;

    private String accessKey;

    @Autowired
    public DatabaseUserDetailsService(PhoneNumberFormatter phoneNumberFormatter, UserSession userSession, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository, @Value("${access.secret.key}") String accessKey) {
        this.phoneNumberFormatter = phoneNumberFormatter;
        this.userSession = userSession;
        this.passwordEncoder = passwordEncoder;
        this.userProfileRepository = userProfileRepository;
        this.accessKey = accessKey;
    }

    @PostConstruct
    public void encryptAccessKey() {
        this.accessKey = passwordEncoder.encode(this.accessKey);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        String username = phoneNumberFormatter.transform(login);

        return new User(username, this.accessKey, Collections.singleton(new SimpleGrantedAuthority(
                userProfileRepository.findByUsername(username).map(userProfile -> {
                    userSession.setUserId(userProfile.getId());
                    return userProfile.getRole();
                }).orElse(SystemRoles.ROLE_UNCONFIRMED)
        )));
    }
}
