package me.riguron.telegram.entity;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UserCredentialsTest extends GenericEntityTest {

    @Test
    public void whenRefreshThenStateRefreshed() {
        UserCredentials userCredentials = new UserCredentials("phone", new byte[]{1, 2, 3}, new UserDataCenter("host", 322));
        assertEquals(userCredentials.getPhone(), "phone");
        assertArrayEquals(userCredentials.getAuthKey(), new byte[]{1, 2, 3});
        userCredentials.refresh(new UserCredentials("new phone", new byte[]{4, 5, 6}, new UserDataCenter("new host", 269)));
        assertEquals(userCredentials.getPhone(), "new phone");
        assertArrayEquals(userCredentials.getAuthKey(), new byte[]{4, 5, 6});
        assertEquals(userCredentials.getDataCenter().getPort(), 269);
        assertEquals(userCredentials.getDataCenter().getHost(), "new host");
    }


    @Override
    protected Object createEntity() {
        return new UserCredentials("phone", new byte[]{1, 2, 3}, new UserDataCenter("host", 226));
    }
}