package me.riguron.telegram;

import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.dump.ChannelDumpService;
import me.riguron.telegram.dump.ChannelDumpTaskFactory;
import me.riguron.telegram.dump.execution.ChannelDumpExecutionManager;
import me.riguron.telegram.file.FileService;
import me.riguron.telegram.file.LocalFileService;
import me.riguron.telegram.file.ValidatingFileService;
import me.riguron.telegram.repository.DumpTaskRepository;
import me.riguron.telegram.repository.UserProfileRepository;
import me.riguron.telegram.user.UserService;
import me.riguron.telegram.date.DateFormat;
import me.riguron.telegram.dump.ChannelDumpService;
import me.riguron.telegram.dump.ChannelDumpTaskFactory;
import me.riguron.telegram.dump.execution.ChannelDumpExecutionManager;
import me.riguron.telegram.file.FileService;
import me.riguron.telegram.file.LocalFileService;
import me.riguron.telegram.file.ValidatingFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public FileService fileService(@Value("${file.repository}") String repository) {
        return new ValidatingFileService(new LocalFileService(repository));
    }

    @Bean
    public ChannelDumpTaskFactory channelDumpTaskFactory(MessageSource messageSource, EditableDocumentFactory editableDocumentFactory, DateFormat dateFormat, FileService fileService) {
        return new ChannelDumpTaskFactory(messageSource, editableDocumentFactory, dateFormat, fileService);
    }

    @Bean
    public ChannelDumpService channelDumpService(@Value("${task.limit}") int taskLimit, DumpTaskRepository dumpTaskRepository, ChannelDumpExecutionManager executionManager, UserProfileRepository userProfileRepository) {
        return new ChannelDumpService(taskLimit, dumpTaskRepository, executionManager, userProfileRepository);
    }

    @Bean
    public ChannelDumpExecutionManager channelDumpExecutionManager(ChannelDumpTaskFactory dumpTaskFactory) {
        return new ChannelDumpExecutionManager(dumpTaskFactory);
    }

    @Bean
    public UserService userService(UserProfileRepository userProfileRepository) {
        return new UserService(userProfileRepository);
    }


}
