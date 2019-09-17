package me.riguron.telegram.repository;

import lombok.extern.slf4j.Slf4j;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.TestConfiguration;
import me.riguron.telegram.entity.UserCredentials;
import me.riguron.telegram.entity.UserDataCenter;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.entity.task.DumpTaskOptions;
import me.riguron.telegram.entity.task.DumpTaskRecord;
import me.riguron.telegram.entity.task.DumpTaskState;
import me.riguron.telegram.projection.TaskProjection;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.projection.TaskProjection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
@Transactional(propagation = Propagation.NEVER)
@Slf4j
public class DumpTaskRepositoryTest {

    private static final String PHONE = "phone";

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DumpTaskRepository dumpTaskRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private DumpTaskRecord dumpTaskRecord;

    private UserProfile userProfile;

    @Before
    public void persistProfile() {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                UserProfile userProfile = new UserProfile(1, "name", PHONE, "role",
                        new UserCredentials(
                                PHONE,
                                new byte[]{1, 2, 3},
                                new UserDataCenter("host", 10)));
                DumpTaskRepositoryTest.this.userProfile = userProfileRepository.save(userProfile);
            }
        });

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                DumpTaskRecord record = new DumpTaskRecord("file", true, new DumpTaskState("Task"), new DumpTaskOptions("channel", DocumentType.PDF, true), DumpTaskRepositoryTest.this.userProfile);
                dumpTaskRecord = dumpTaskRepository.save(record);
            }
        });

    }

    @Test
    public void whenFileNullThenPersistedAndFetched() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                log.info("Find record by ID");

                Optional<DumpTaskRecord> fetchedRecordOptional = dumpTaskRepository.findById(dumpTaskRecord.getId());
                assertTrue(fetchedRecordOptional.isPresent());

                log.info("Found");

                DumpTaskRecord fetchedRecord = fetchedRecordOptional.get();

                log.info("Fetch parent entity");
                fetchedRecord.getDumpTaskOptions().getChannel();

                log.info("Fetched");


                assertRecord(fetchedRecord);
            }
        });
    }

    private void assertRecord(DumpTaskRecord fetchedRecord) {
        assertNotSame(dumpTaskRecord, fetchedRecord);
        assertEquals(dumpTaskRecord, fetchedRecord);

        assertNotNull(fetchedRecord);

        assertNotNull(fetchedRecord.getFile());
        assertEquals("file", fetchedRecord.getFile());

        assertTrue(fetchedRecord.isCompleted());
        assertNotNull(fetchedRecord.getDumpTaskState());
        assertEquals("Task", fetchedRecord.getDumpTaskState().getDescription());
        assertNotNull(fetchedRecord.getDumpTaskOptions());
        assertEquals("channel", fetchedRecord.getDumpTaskOptions().getChannel());
        assertEquals(DocumentType.PDF, fetchedRecord.getDumpTaskOptions().getType());
        assertTrue(fetchedRecord.getDumpTaskOptions().isImages());
        assertNotNull(fetchedRecord.getUserProfile());
        assertEquals(1, fetchedRecord.getUserProfile().getId().intValue());
        assertEquals("name", fetchedRecord.getUserProfile().getName());
        assertEquals(PHONE, fetchedRecord.getUserProfile().getPhone());
        assertNotNull(fetchedRecord.getUserProfile().getUserCredentials());
        assertArrayEquals(new byte[]{1, 2, 3}, fetchedRecord.getUserProfile().getUserCredentials().getAuthKey());
        assertNotNull(fetchedRecord.getUserProfile().getUserCredentials().getDataCenter());
        assertEquals("host", fetchedRecord.getUserProfile().getUserCredentials().getDataCenter().getHost());
        assertEquals(10, fetchedRecord.getUserProfile().getUserCredentials().getDataCenter().getPort());

    }

    @After
    public void cleanUp() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                dumpTaskRepository.deleteAll();
                userProfileRepository.deleteAll();
            }
        });
    }


    @Test
    public void whenLoadProjectionThenLoaded() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                List<TaskProjection> projections = dumpTaskRepository.getTaskHistory(1, Sort.by(Sort.Order.asc("date")));
                assertEquals(1, projections.size());
                TaskProjection projection = projections.get(0);
                assertEquals("channel", projection.getChannelName());
                Assert.assertEquals(DocumentType.PDF, projection.getDocumentType());
                assertEquals("file", projection.getFile());
                assertEquals("Task", projection.getState());
            }
        });
    }

}