package me.riguron.telegram.rest.controller;

import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.dto.HistoryDumpTaskView;
import me.riguron.telegram.dto.mapper.TaskMapper;
import me.riguron.telegram.dump.ChannelDumpService;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.dto.mapper.TaskMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class TaskController {

    private TaskMapper taskMapper;

    private ChannelDumpService channelDumpService;

    private DateFormat dateFormat;

    public TaskController(TaskMapper taskMapper, ChannelDumpService channelDumpService, DateFormat dateFormat) {
        this.taskMapper = taskMapper;
        this.channelDumpService = channelDumpService;
        this.dateFormat = dateFormat;
    }

    @GetMapping(value = "/history/{userId}", produces = "application/json")
    @PreAuthorize("#userId == @userSession.userId")
    public List<HistoryDumpTaskView> listTasks(@PathVariable Integer userId, Locale locale) {
        return taskMapper.mapTaskHistory(channelDumpService.getHistory(userId), locale, dateFormat);
    }

}
