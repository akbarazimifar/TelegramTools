package me.riguron.telegram.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.ModelResultMatchers;

import java.util.function.Consumer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@RunWith(SpringRunner.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void whenErrorAttributeThenFailed() throws Exception {
        performGetAndExpect(mockHttpServletRequestBuilder -> mockHttpServletRequestBuilder.param("error", "Login failed"),
                modelResultMatchers -> modelResultMatchers.attribute("error", "Login failed"));
    }

    @Test
    public void whenFlashAttributeThenFailed() throws Exception {
        performGetAndExpect(mockHttpServletRequestBuilder -> mockHttpServletRequestBuilder.flashAttr("error", "Flash attribute"),
                modelResultMatchers -> modelResultMatchers.attribute("error", "Flash attribute"));
    }

    private void performGetAndExpect(Consumer<MockHttpServletRequestBuilder> requestBuilder, Consumer<ModelResultMatchers> expectation) throws Exception {
        MockHttpServletRequestBuilder request = get("/login");
        requestBuilder.accept(request);

        mockMvc.perform(
                request
        ).andExpect(
                status().isOk()
        );

        expectation.accept(model());
    }
}