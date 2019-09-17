package me.riguron.telegram.dump;

import com.github.badoualy.telegram.tl.api.TLInputPeerChannel;
import me.riguron.telegram.DocumentType;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.dump.state.*;
import me.riguron.telegram.dump.text.TextDump;
import me.riguron.telegram.task.ChannelSearchTask;
import me.riguron.telegram.task.fetch.ChannelFetchTask;
import me.riguron.telegram.task.fetch.MessageFetchResult;
import me.riguron.telegram.channel.ChannelMessage;
import me.riguron.telegram.dump.state.*;
import me.riguron.telegram.task.ChannelSearchTask;
import me.riguron.telegram.task.fetch.ChannelFetchTask;
import me.riguron.telegram.task.fetch.MessageFetchResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ChannelDumpTaskTest {

    private ChannelDumpTask channelDumpTask;

    private ChannelSearchTask channelSearchTask;

    private ChannelFetchTask channelFetchTask;

    @Before
    public void doPrepare() {

        this.channelSearchTask = mock(ChannelSearchTask.class);
        this.channelFetchTask = mock(ChannelFetchTask.class);

        TextDump textDump = mock(TextDump.class);
        doAnswer(invocationOnMock -> {
            assertState(FileCreatingState.class);
            return null;
        }).when(textDump).dump();

        DumpOptions dumpOptions = new DumpOptions("channel", DocumentType.PDF, true, new File("output.pdf"), 0);

        this.channelDumpTask = new ChannelDumpTask(channelSearchTask, channelFetchTask, dumpOptions, messages -> mock(TextDump.class));
        channelDumpTask.setStarted();
        assertState(InitializingState.class);


    }

    @Test
    public void whenBothSearchAndFetchOkThenExecuted() {
        runAndTest(false, false, DoneState.class, false);
    }

    @Test
    public void whenSearchFailsThenFailureStateWithChannel() {
        runAndTest(false, true, SearchingChannelState.class, true);
    }

    @Test
    public void whenFetchFailsThenFailureStateWithFetch() {
        runAndTest(true, false, NoNewMessagesState.class, true);
    }

    private void runAndTest(boolean fetch, boolean search, Class<? extends ChannelDumpState> type, boolean failure) {
        fetch(fetch);
        search(search);
        channelDumpTask.run();
        if (failure) {
            assertFailure(type);
        } else {
            assertState(DoneState.class);
        }
        assertEquals(failure, !channelDumpTask.isCompleted());
    }

    private void fetch(boolean returnEmptyContainer) {
        when(channelFetchTask.fetch(any(), anyBoolean(), anyInt())).thenAnswer(
                invocationOnMock -> {
                    assertState(FetchingMessagesState.class);
                    return new MessageFetchResult(
                            0,
                            !returnEmptyContainer ? Collections.singletonList(
                                    new ChannelMessage(1L, "text", new byte[]{1, 2, 3})
                            ) : Collections.emptyList());
                }
        );
    }

    private void search(boolean returnEmptyOptional) {
        when(channelSearchTask.searchChannel(eq("channel"))).thenAnswer((Answer<Optional<TLInputPeerChannel>>) invocationOnMock -> {
            assertState(SearchingChannelState.class);
            if (returnEmptyOptional) {
                return Optional.empty();
            }
            TLInputPeerChannel tlInputPeerChannel = new TLInputPeerChannel();
            return Optional.of(tlInputPeerChannel);
        });
    }

    private void assertState(Class<? extends ChannelDumpState> stateType) {
        assertEquals(stateType, channelDumpTask.getState().getClass());
    }

    private void assertFailure(Class<? extends ChannelDumpState> wrappedFailure) {
        assertState(FailureState.class);
        ChannelDumpState wrappedFailureField = (ChannelDumpState) ReflectionTestUtils.getField(
                channelDumpTask.getState(), "failedState"
        );
        assertEquals(wrappedFailure, wrappedFailureField.getClass());
    }
}