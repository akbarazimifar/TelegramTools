package me.nextgeneric.telegram.entity.task;

import me.nextgeneric.telegram.entity.GenericEntityTest;

public class DumpTaskStateTest extends GenericEntityTest {

    @Override
    protected Object createEntity() {
        return new DumpTaskState("description");
    }
}