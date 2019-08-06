package me.nextgeneric.telegram.dump;

import me.nextgeneric.telegram.repository.UserProfileRepository;
import me.nextgeneric.telegram.user.UserSession;
import me.nextgeneric.telegram.DocumentType;
import me.nextgeneric.telegram.dump.execution.ChannelDumpExecutionManager;
import me.nextgeneric.telegram.dump.state.ChannelDumpState;
import me.nextgeneric.telegram.entity.UserCredentials;
import me.nextgeneric.telegram.entity.UserProfile;
import me.nextgeneric.telegram.repository.DumpTaskRepository;
import org.junit.Test;

import java.io.File;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SuppressWarnings("unchecked")
public class ChannelDumpServiceTest {

    @Test
    public void testDump() {

        ChannelDumpExecutionManager channelDumpManager = mock(ChannelDumpExecutionManager.class);

        UserSession userSession = new UserSession();
        userSession.setUserId(1);
        userSession.setPhoneNumber("phone");

        ChannelDumpService channelDumpService = new ChannelDumpService(3, mock(DumpTaskRepository.class), channelDumpManager, mock(UserProfileRepository.class));

        CompletableFuture future = mock(CompletableFuture.class);
        when(channelDumpManager.dump(any(), any(), any())).thenReturn(future);

        assertTrue(channelDumpService.dump(new DumpRequest("channel", DocumentType.PDF, false, false, Locale.ENGLISH), userSession,
                new UserProfile(1, "name surname", "phone", "role", mock(UserCredentials.class))));

        verify(channelDumpManager, times(1)).dump(any(), any(), any());
        verify(future, times(1)).thenAccept(any());

    }

    @Test
    public void doTestSave() {

        UserProfileRepository userProfileRepository = mock(UserProfileRepository.class);

        ChannelDumpService channelDumpService = new ChannelDumpService(3, mock(DumpTaskRepository.class), mock(ChannelDumpExecutionManager.class), userProfileRepository);

        UserProfile userProfile = new UserProfile(1, "name", "phone", "role", null);
        ChannelDumpTask channelDumpTask = mock(ChannelDumpTask.class);
        DumpResult dumpResult = new DumpResult(channelDumpTask, 1);

        when(userProfileRepository.findById(userProfile.getId())).thenReturn(Optional.of(userProfile));

        when(channelDumpTask.getOutput()).thenReturn(new File("src/test/resources/1.txt"));
        when(channelDumpTask.getState()).thenReturn(mock(ChannelDumpState.class));
        channelDumpService.save(dumpResult, new DumpRequest("channel", DocumentType.PDF, true, false, Locale.ENGLISH), userProfile);

        assertEquals(userProfile.getLastMessageId("channel"), 1);

    }

}