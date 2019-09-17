package me.riguron.telegram.entity.task;

import me.riguron.telegram.DocumentType;
import me.riguron.telegram.entity.GenericEntityTest;
import me.riguron.telegram.entity.GenericEntityTest;

public class DumpTaskOptionsTest extends GenericEntityTest {

    @Override
    protected Object createEntity() {
        return new DumpTaskOptions("channel", DocumentType.PDF, true);
    }
}