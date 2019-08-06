package me.nextgeneric.telegram.repository;

import me.nextgeneric.telegram.TestConfiguration;
import me.nextgeneric.telegram.entity.UserCredentials;
import me.nextgeneric.telegram.entity.UserDataCenter;
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

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
@Transactional(propagation = Propagation.NEVER)
public class UserCredentialsRepositoryTest {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    private UserCredentials userCredentials;

    @Before
    public void persistCredentials() {

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    byte[] key = new byte[]{0xA, 0xB, 0xC, 0xF};
                    userCredentials = new UserCredentials(
                            "phone",
                            key,
                            new UserDataCenter("host", 10000)
                    );
                    userCredentialsRepository.save(userCredentials);
                }
            });


    }

    @After
    public void cleanUp() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                userCredentialsRepository.deleteAll();
            }
        });
    }

    @Test
    public void whenLoadThenEqualsToPersisted() {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                Optional<UserCredentials> optionalCredentials = userCredentialsRepository.findById("phone");
                assertTrue(optionalCredentials.isPresent());
                optionalCredentials.ifPresent(fetchedCredentials -> {
                    assertNotSame(userCredentials, fetchedCredentials);
                    assertEquals(userCredentials, fetchedCredentials);
                    assertNotNull(fetchedCredentials.getDataCenter());
                    assertEquals("host", fetchedCredentials.getDataCenter().getHost());
                    assertEquals(10000, fetchedCredentials.getDataCenter().getPort());
                });

            }
        });
    }


}