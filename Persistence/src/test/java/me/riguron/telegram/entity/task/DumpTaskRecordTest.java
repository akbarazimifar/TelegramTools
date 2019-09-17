package me.riguron.telegram.entity.task;

import me.riguron.telegram.entity.GenericEntityTest;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.entity.GenericEntityTest;
import me.riguron.telegram.entity.UserProfile;

public class DumpTaskRecordTest extends GenericEntityTest {

    @Override
    protected Object createEntity() {
        return new DumpTaskRecord("file", true,
                new DumpTaskState(),
                new DumpTaskOptions(),
                new UserProfile());
    }
}