package me.nextgeneric.telegram.entity.task;

import me.nextgeneric.telegram.entity.GenericEntityTest;
import me.nextgeneric.telegram.entity.UserProfile;

public class DumpTaskRecordTest extends GenericEntityTest {

    @Override
    protected Object createEntity() {
        return new DumpTaskRecord("file", true,
                new DumpTaskState(),
                new DumpTaskOptions(),
                new UserProfile());
    }
}