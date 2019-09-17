package me.riguron.telegram.file;

import me.riguron.telegram.SystemRoles;
import me.riguron.telegram.SystemRoles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DownloadController.class)
@RunWith(SpringRunner.class)
public class DownloadControllerTest {

    private static final String OUT = "222222_THREAD_HASH-out.txt";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocalFileService fileService;

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenDownloadExistingResourceThenDownloaded() throws Exception {

        File file = Paths.get("src", "test", "resources").resolve(OUT).toFile();
        when(fileService.getOriginalName(eq(OUT))).thenReturn("out.txt");
        when(fileService.getFileLocation(eq(OUT))).thenReturn(file.toPath());

        this.mockMvc.perform(
                get("/download/" + OUT)
        ).andDo(
                print()
        ).andExpect(
                header().string("Content-Disposition", "form-data; name=\"attachment\"; filename=\"out.txt\"")
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string("Text file contents")
        );
    }

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenPathIsEmptyThen404Returned() throws Exception {

        when(fileService.getOriginalName(eq("absent"))).thenReturn("absent");
        when(fileService.getFileLocation(any())).thenReturn(Paths.get("a/b/c"));

        this.mockMvc.perform(
                get("/download/absent")
        ).andExpect(
                status().isNotFound()
        );
    }

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenDownloadInexistentFileThen404() throws Exception {
        when(fileService.getOriginalName(eq("absent"))).thenReturn("absent");
        when(fileService.getFileLocation(any())).thenReturn(Paths.get("a/b/c"));
        getAndExpect404("/download/absent");
    }

    @Test
    @WithMockUser(roles = SystemRoles.FULL)
    public void whenDoNotSpecifyUrlThenNotFound() throws Exception {
        getAndExpect404("/download/");
    }

    private void getAndExpect404(String target) throws Exception {
        this.mockMvc.perform(
                get(target)
        ).andDo(
                print()
        ).andExpect(
                status().isNotFound()
        );
    }
}