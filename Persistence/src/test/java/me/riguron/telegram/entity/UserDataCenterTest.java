package me.riguron.telegram.entity;

public class UserDataCenterTest extends GenericEntityTest {

    @Override
    protected Object createEntity() {
        return new UserDataCenter("host", 226);
    }
}