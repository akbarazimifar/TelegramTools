package me.riguron.telegram.application;

import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.dto.mapper.TaskMapper;
import me.riguron.telegram.dump.ChannelDumpService;
import me.riguron.telegram.dump.execution.ChannelDumpExecutionManager;
import me.riguron.telegram.user.UserSession;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.dto.mapper.TaskMapper;
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
