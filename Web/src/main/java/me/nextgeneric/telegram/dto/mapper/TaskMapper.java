package me.nextgeneric.telegram.dto.mapper;

import me.nextgeneric.telegram.date.DateFormat;
import me.nextgeneric.telegram.dto.ActiveDumpTaskView;
import me.nextgeneric.telegram.dto.HistoryDumpTaskView;
import me.nextgeneric.telegram.dump.ChannelDumpTask;
import me.nextgeneric.telegram.projection.TaskProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private MessageSource messageSource;

    @Autowired
    public TaskMapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public List<HistoryDumpTaskView> mapTaskHistory(List<TaskProjection> historyProjections, Locale locale, DateFormat dateFormat) {
        return historyProjections.stream()
                .map(taskProjection -> new HistoryDumpTaskView(
                        dateFormat.format(taskProjection.getDate(), locale),
                        taskProjection.getChannelName(),
                        taskProjection.isImages(),
                        taskProjection.getDocumentType(),
                        taskProjection.getFile(),
                        stateMessage(taskProjection, locale),
                        taskProjection.isCompleted()
                ))
                .collect(Collectors.toList());
    }

    public List<ActiveDumpTaskView> mapActiveTasks(List<ChannelDumpTask> activeTasks, Locale locale, DateFormat format) {
        return activeTasks.stream()
                .map(task -> new ActiveDumpTaskView(
                        format.format(task.getDateStarted(), locale),
                        task.getOptions().getChannel(),
                        task.getOptions().getDocumentType().getExtension(),
                        messageSource.getMessage(task.getState().publicDescription(), task.getState().getStateVariables().toArray(), locale)
                ))
                .collect(Collectors.toList());
    }

    private String stateMessage(TaskProjection taskProjection, Locale locale) {
        String state = messageSource.getMessage(taskProjection.getState(), new Object[]{}, locale);
        if (taskProjection.isCompleted()) {
            return state;
        } else {
            return messageSource.getMessage("execution.state.failure", new String[]{state}, locale);
        }
    }
}
