package me.riguron.telegram.repository;

import lombok.extern.slf4j.Slf4j;
import me.riguron.telegram.TestConfiguration;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.entity.UserCredentials;
import me.riguron.telegram.entity.UserDataCenter;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.entity.task.DumpTaskOptions;
import me.riguron.telegram.entity.task.DumpTaskRecord;
import me.riguron.telegram.entity.task.DumpTaskState;
import me.riguron.telegram.entity.UserProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
@Transactional(propagation = Propagation.NEVER)
@Slf4j

public class UserProfileRepositoryTest {

    private static final String PHONE = "phone";

    private static final byte[] KEY = new byte[]{1, 2, 3, 4, 5, 7};

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private UserProfile userProfile;

    @Before
    public void createProfile() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                UserProfile userProfileLocal = new UserProfile(1, "name", PHONE, "role", new UserCredentials(PHONE, KEY, new UserDataCenter("host", 20000)));
                userProfile = userProfileRepository.save(userProfileLocal);
                userProfile.addRecord(new DumpTaskRecord("file", true, new DumpTaskState("Task"),
                        new DumpTaskOptions("channel", DocumentType.PDF, true), userProfile));
                userProfile.setLastMessageId("channel1", 25);
                userProfile.setLastMessageId("channel2", 26);
            }
        });
    }

    @Test
    public void whenUpdateLastMessageIdThenUpdated() {


        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Optional<UserProfile> found = userProfileRepository.findById(1);
                assertTrue(found.isPresent());
                UserProfile foundProfile = found.get();
                log.info("Set last message id");
                foundProfile.setLastMessageId("new_channel", 67);
            }
        });

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Optional<UserProfile> found = userProfileRepository.findById(1);
                assertTrue(found.isPresent());
                UserProfile foundProfile = found.get();
                assertEquals(67, foundProfile.getLastMessageId("new_channel"));
                assertEquals(26, foundProfile.getLastMessageId("channel2"));
            }
        });

    }


    @Test
    public void whenFindByIdThenEqualsCreated() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                log.info("Load user by id");

                Optional<UserProfile> found = userProfileRepository.findById(1);
                log.info("Loaded");

                assertTrue(found.isPresent());

                UserProfile foundProfile = found.get();

                log.info("Lazily calling getUserCredentials()");

                log.info("User's phone: " + Arrays.toString(foundProfile.getUserCredentials().getAuthKey()));

                log.info("Called");
                assertProfile(foundProfile);
            }
        });
    }


    @Test
    public void whenUpdateCredentialsThenUpdated() {

        UserCredentials userCredentials = new UserCredentials("another_phone", new byte[]{2, 3, 4}, new UserDataCenter("new_host", 222222));

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                log.info("Load user by id");
                Optional<UserProfile> found = userProfileRepository.findByUsernameWithCredentials(PHONE);
                assertTrue(found.isPresent());
                assertNotSame(userProfile, found.get());
                userProfile = found.get();
                log.info("Set user credentials: begin");

                userProfile.getUserCredentials().setAuthKey(userCredentials.getAuthKey());
                userProfile.getUserCredentials().setDataCenter(userCredentials.getDataCenter());

                log.info("Set user credentials: done. Committing now");
                // commit
            }
        });

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                log.info("Load user again, after updating");
                Optional<UserProfile> found = userProfileRepository.findById(1);
                assertTrue(found.isPresent());
                UserProfile fetchedProfile = found.get();
                assertNotSame(userProfile, fetchedProfile);
                log.info("Fetch credentials lazily and compare");
                assertNotSame(userCredentials, fetchedProfile.getUserCredentials());
                assertEquals(userCredentials.getDataCenter(), fetchedProfile.getUserCredentials().getDataCenter());
                assertArrayEquals(userCredentials.getAuthKey(), fetchedProfile.getUserCredentials().getAuthKey());
                log.info("Done");
            }
        });

    }


    @Test
    public void whenFindByUsernameThenFound() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Optional<UserProfile> loadedProfileOptional = userProfileRepository.findByUsername(PHONE);
                assertTrue(loadedProfileOptional.isPresent());
                loadedProfileOptional.ifPresent(loadedProfile -> assertProfile(loadedProfile));
            }
        });
    }

    @Test
    public void whenFindByUsernameWithCredentialsThenFound() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Optional<UserProfile> loadedProfileOptional = userProfileRepository.findByUsernameWithCredentials(PHONE);
                assertTrue(loadedProfileOptional.isPresent());
                loadedProfileOptional.ifPresent(loadedProfile -> assertProfile(loadedProfile));
            }
        });
    }

    private void assertProfile(UserProfile foundProfile) {
        assertNotNull(foundProfile);
        assertNotSame(userProfile, foundProfile);
        assertEquals(userProfile, foundProfile);
        assertEquals(userProfile.getUserCredentials(), foundProfile.getUserCredentials());
        assertEquals(1, foundProfile.getRecords().size());
        assertEquals(1, (int) foundProfile.getRecords().get(0).getUserProfile().getId());
        assertEquals(new ArrayList<>(userProfile.getRecords()), new ArrayList<>(foundProfile.getRecords()));
        assertEquals(userProfile.getLastMessages(), foundProfile.getLastMessages());
    }

    @After
    public void clear() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                userProfileRepository.deleteAll();
            }
        });

    }

}