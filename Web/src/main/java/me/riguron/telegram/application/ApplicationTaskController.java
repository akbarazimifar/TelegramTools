package me.riguron.telegram.application;

import me.riguron.telegram.DocumentType;
import me.riguron.telegram.channel.ChannelParser;
import me.riguron.telegram.dto.DumpRequestDto;
import me.riguron.telegram.dump.ChannelDumpService;
import me.riguron.telegram.dump.DumpRequest;
import me.riguron.telegram.entity.UserProfile;
import me.riguron.telegram.user.UserService;
import me.riguron.telegram.user.UserSession;
import me.riguron.telegram.channel.ChannelParser;
import me.riguron.telegram.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ApplicationTaskController {

    private MessageSource messageSource;

    private UserService userService;

    private UserSession userSession;

    private ChannelDumpService channelDumpService;

    private ChannelParser channelParser;

    @Autowired
    public ApplicationTaskController(MessageSource messageSource, UserService userService, UserSession userSession, ChannelDumpService channelDumpService, ChannelParser channelParser) {
        this.messageSource = messageSource;
        this.userService = userService;
        this.userSession = userSession;
        this.channelDumpService = channelDumpService;
        this.channelParser = channelParser;
    }

    @PostMapping("/application")
    public RedirectView createTask(@Valid @ModelAttribute("dumpRequestDto") DumpRequestDto dumpRequestDto, BindingResult result, Locale locale, RedirectAttributes attributes) {

        RedirectView target = new RedirectView("application");

        if (result.hasErrors()) {
            attributes.addFlashAttribute("error", messageSource.getMessage(result.getFieldError(), locale));
            return target;
        }

        DumpRequest dumpRequest = new DumpRequest(
                channelParser.parse(dumpRequestDto.getChannelName()),
                DocumentType.valueOf(dumpRequestDto.getDocumentType().toUpperCase()),
                dumpRequestDto.isImages(),
                dumpRequestDto.isLoadOnlyNew(),
                locale
        );

        UserProfile userProfile = userService.findByUsername(userSession.getPhoneNumber()).orElseThrow(IllegalStateException::new);

        if (!channelDumpService.dump(dumpRequest, userSession, userProfile)) {
            attributes.addFlashAttribute("error", "Failed to execute task");
        }

        return target;

    }

}
