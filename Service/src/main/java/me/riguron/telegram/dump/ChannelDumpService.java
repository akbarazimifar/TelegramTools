package me.riguron.telegram.dump;

import lombok.extern.slf4j.Slf4j;
import me.riguron.telegram.dump.execution.ChannelDumpExecutionManager;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.entity.task.DumpTaskOptions;
import me.riguron.telegram.entity.task.DumpTaskRecord;
import me.riguron.telegram.entity.task.DumpTaskState;
import me.riguron.telegram.projection.TaskProjection;
import me.riguron.telegram.repository.DumpTaskRepository;
import me.riguron.telegram.repository.UserProfileRepository;
import me.riguron.telegram.user.UserSession;
import me.riguron.telegram.dump.execution.ChannelDumpExecutionManager;
import me.riguron.telegram.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
public class ChannelDumpService {

    private ChannelDumpService selfDelegate;

    private final int taskLimitPerUser;

    private final DumpTaskRepository dumpTaskRepository;

    private final ChannelDumpExecutionManager channelDumper;

    private final UserProfileRepository userProfileRepository;

    public ChannelDumpService(int taskLimitPerUser, DumpTaskRepository dumpTaskRepository, ChannelDumpExecutionManager channelDumper, UserProfileRepository userProfileRepository) {
        this.taskLimitPerUser = taskLimitPerUser;
        this.dumpTaskRepository = dumpTaskRepository;
        this.channelDumper = channelDumper;
        this.userProfileRepository = userProfileRepository;
    }

    public boolean dump(DumpRequest request, UserSession userSession, UserProfile userProfile) {
        synchronized (this.channelDumper) {
            if (channelDumper.getActiveTaskCount(userSession.getUserId()) < taskLimitPerUser) {
                channelDumper
                        .dump(userProfile, userSession.getTelegramClient(), request)
                        .thenAccept(dumpResult -> selfDelegate.save(dumpResult, request, userProfile));
                return true;
            }
            return false;
        }
    }

    @Transactional
    public void save(DumpResult result, DumpRequest dumpRequest, UserProfile userProfile) {

        UserProfile freshProfile = userProfileRepository.findById(userProfile.getId()).orElseThrow(IllegalStateException::new);
        ChannelDumpTask dumpTask = result.getTask();

        dumpTaskRepository.save(
                new DumpTaskRecord(
                        dumpTask.getOutput().getName(),
                        dumpTask.isCompleted(),
                        new DumpTaskState(dumpTask.getFinalDescription()),
                        new DumpTaskOptions(dumpRequest.getChannelName(), dumpRequest.getDocumentType(), dumpRequest.isImages()),
                        freshProfile
                ));

        if (result.getId() > 0) {
            freshProfile.setLastMessageId(dumpRequest.getChannelName(), result.getId());
        }
    }


    /**
     * Returns the history of user's tasks.
     *
     * @param userId id of the user which the tasks are fetched for
     * @return task history
     */

    @Transactional(readOnly = true)
    public List<TaskProjection> getHistory(Integer userId) {
        return dumpTaskRepository.getTaskHistory(userId, Sort.by(Sort.Order.desc("date")));
    }

    @Autowired
    public void setSelfDelegate(ChannelDumpService selfDelegate) {
        this.selfDelegate = selfDelegate;
    }
}
