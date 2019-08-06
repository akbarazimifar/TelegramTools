package me.nextgeneric.telegram.dump.execution;

import com.github.badoualy.telegram.api.TelegramClient;
import me.nextgeneric.telegram.DocumentType;
import me.nextgeneric.telegram.dump.ChannelDumpTask;
import me.nextgeneric.telegram.dump.ChannelDumpTaskFactory;
import me.nextgeneric.telegram.dump.DumpRequest;
import me.nextgeneric.telegram.entity.UserCredentials;
import me.nextgeneric.telegram.entity.UserProfile;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChannelDumpExecutionManagerTest {

    private ChannelDumpTask dumpTask;

    private ChannelDumpExecutionManager channelDumpManager;

    @Before
    public void preTest() throws IOException {
        ChannelDumpTaskFactory factory = mock(ChannelDumpTaskFactory.class);

        this.channelDumpManager = spy(new ChannelDumpExecutionManager(factory));
        this.dumpTask = mock(ChannelDumpTask.class);
        when(factory.createChannelDumpTask(any(), any(), any())).thenReturn(dumpTask);
    }


    @Test
    public void testDump() throws ExecutionException, InterruptedException, IOException {

        doAnswer(invocationOnMock -> {
            assertEquals(1, channelDumpManager.getActiveTaskCount(1));
            assertEquals(1, channelDumpManager.getActiveTasks(1).size());
            return null;
        }).when(dumpTask).run();

        channelDumpManager.dump(
                new UserProfile(1, "name", "phone", "role", mock(UserCredentials.class)), mock(TelegramClient.class),
                new DumpRequest("channel", DocumentType.PDF, true, true, Locale.ENGLISH)
        ).get();

        verify(channelDumpManager).add(anyInt(), any());
        verify(channelDumpManager).remove(anyInt(), any());

        channelDumpManager.terminateExecutor();
    }
}