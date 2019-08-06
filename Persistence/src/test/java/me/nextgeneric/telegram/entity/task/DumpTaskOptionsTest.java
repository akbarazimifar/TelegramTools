package me.nextgeneric.telegram.entity.task;

import me.nextgeneric.telegram.DocumentType;
import me.nextgeneric.telegram.entity.GenericEntityTest;

public class DumpTaskOptionsTest extends GenericEntityTest {

    @Override
    protected Object createEntity() {
        return new DumpTaskOptions("channel", DocumentType.PDF, true);
    }
}