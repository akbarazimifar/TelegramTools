package me.nextgeneric.telegram.dump.execution;

import com.github.badoualy.telegram.api.TelegramClient;
import me.nextgeneric.telegram.dump.ChannelDumpTask;
import me.nextgeneric.telegram.dump.ChannelDumpTaskFactory;
import me.nextgeneric.telegram.dump.DumpRequest;
import me.nextgeneric.telegram.dump.DumpResult;
import me.nextgeneric.telegram.entity.UserProfile;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

public class ChannelDumpExecutionManager {

    private final Map<Integer, List<ChannelDumpTask>> tasks = new ConcurrentHashMap<>();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final ChannelDumpTaskFactory channelDumpTaskFactory;

    public ChannelDumpExecutionManager(ChannelDumpTaskFactory channelDumpTaskFactory) {
        this.channelDumpTaskFactory = channelDumpTaskFactory;
    }

    public CompletableFuture<DumpResult> dump(UserProfile userProfile, TelegramClient telegramClient, DumpRequest request) {
        ChannelDumpTask task = channelDumpTaskFactory.createChannelDumpTask(request, userProfile, telegramClient);

        return CompletableFuture.supplyAsync(() -> {
            add(userProfile.getId(), task);
            try {
                task.setStarted();
                return new DumpResult(task, task.run());
            } catch (Exception e) {
                e.printStackTrace();
                return new DumpResult(task, 0);
            } finally {
                this.remove(userProfile.getId(), task);
            }

        }, executorService);
    }

    public int getActiveTaskCount(Integer userId) {
        return compute(userId, Function.identity()).size();
    }

    public List<ChannelDumpTask> getActiveTasks(Integer userId) {
        return compute(userId, Function.identity());
    }

    public void add(int userId, ChannelDumpTask task) {
        compute(userId, channelDumpTasks -> channelDumpTasks.add(task));
    }

    public void remove(Integer id, ChannelDumpTask task) {
        compute(id, channelDumpTasks -> channelDumpTasks.remove(task));
    }

    private <T> T compute(Integer id, Function<List<ChannelDumpTask>, T> function) {
        return function.apply(tasks.computeIfAbsent(id, x -> new CopyOnWriteArrayList<>()));
    }

    @PreDestroy
    public void terminateExecutor() {
        executorService.shutdown();
    }


}
