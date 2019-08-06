package me.nextgeneric.telegram;

import me.nextgeneric.telegram.date.DateFormat;
import me.nextgeneric.telegram.dump.ChannelDumpService;
import me.nextgeneric.telegram.dump.ChannelDumpTaskFactory;
import me.nextgeneric.telegram.dump.execution.ChannelDumpExecutionManager;
import me.nextgeneric.telegram.file.FileService;
import me.nextgeneric.telegram.file.LocalFileService;
import me.nextgeneric.telegram.file.ValidatingFileService;
import me.nextgeneric.telegram.repository.DumpTaskRepository;
import me.nextgeneric.telegram.repository.UserProfileRepository;
import me.nextgeneric.telegram.user.UserService;
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
