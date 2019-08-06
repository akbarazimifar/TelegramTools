package me.nextgeneric.telegram.application;

import me.nextgeneric.telegram.date.DateFormat;
import me.nextgeneric.telegram.dto.mapper.TaskMapper;
import me.nextgeneric.telegram.dump.ChannelDumpService;
import me.nextgeneric.telegram.dump.execution.ChannelDumpExecutionManager;
import me.nextgeneric.telegram.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class ApplicationController {

    private DateFormat dateFormat;

    private ChannelDumpService channelDumpService;

    private ChannelDumpExecutionManager channelDumpExecutionManager;

    private UserSession userSession;

    private TaskMapper taskMapper;

    @Autowired
    public ApplicationController(DateFormat format, ChannelDumpService channelDumpService, ChannelDumpExecutionManager channelDumpExecutionManager, UserSession userSession, TaskMapper taskMapper) {
        this.dateFormat = format;
        this.channelDumpService = channelDumpService;
        this.channelDumpExecutionManager = channelDumpExecutionManager;
        this.userSession = userSession;
        this.taskMapper = taskMapper;
    }

    @GetMapping("/application")
    public String view(Model model, Locale locale) {
        model.addAttribute("language", locale.getLanguage() + ".png");
        model.addAttribute("tasks", taskMapper.mapActiveTasks(channelDumpExecutionManager.getActiveTasks(userSession.getUserId()), locale, dateFormat));
        model.addAttribute("history", taskMapper.mapTaskHistory(channelDumpService.getHistory(userSession.getUserId()), locale, dateFormat));
        return "application";
    }




}
