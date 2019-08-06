package me.nextgeneric.telegram.entity;

import me.nextgeneric.telegram.entity.task.DumpTaskOptions;
import me.nextgeneric.telegram.entity.task.DumpTaskRecord;
import me.nextgeneric.telegram.entity.task.DumpTaskState;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserProfileTest extends GenericEntityTest {

    @Test
    public void whenSetLastMessageIdThenSet() {
        UserProfile userProfile = new UserProfile(1, "name", "phone", "role", new UserCredentials());

        userProfile.setLastMessageId("channel", 1);
        userProfile.setLastMessageId("channel2", 2);

        assertEquals(1, userProfile.getLastMessageId("channel"));
        assertEquals(2, userProfile.getLastMessageId("channel2"));

    }

    @Override
    protected Object createEntity() {
        UserProfile userProfile = new UserProfile(1, "user name", "phone", "role", new UserCredentials("phone", new byte[]{1, 2, 3},
                new UserDataCenter("host", 1)));

        userProfile.addRecord(new DumpTaskRecord("file", true, new DumpTaskState(), new DumpTaskOptions(), userProfile));
        userProfile.setLastMessageId("1", 1);
        return userProfile;
    }
}