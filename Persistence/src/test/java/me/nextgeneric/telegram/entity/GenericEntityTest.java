package me.nextgeneric.telegram.entity;

import org.junit.Test;

import static org.junit.Assert.*;

public abstract class GenericEntityTest {

    @Test
    public void assertThatCreatedEntitiesAreNotSame() {
        assertNotSame(createEntity(), createEntity());
    }

    @Test
    public void whenCompareTwoSameEntitiesThenEquals() {
        assertTrue(createEntity().equals(createEntity()));
    }

    @Test
    public void whenCompareHashCodesOfTwoSameEntitiesThenEquals() {
        assertEquals(createEntity().hashCode(), createEntity().hashCode());
    }

    @Test
    public void whenCompareStringRepresentationsThenEquals() {
        assertEquals(createEntity().toString(), createEntity().toString());
    }

    protected abstract Object createEntity();

}
