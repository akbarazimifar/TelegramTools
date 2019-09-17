package me.riguron.telegram.entity.task;

import me.riguron.telegram.entity.GenericEntityTest;

public class DumpTaskStateTest extends GenericEntityTest {

    @Override
    protected Object createEntity() {
        return new DumpTaskState("description");
    }
}