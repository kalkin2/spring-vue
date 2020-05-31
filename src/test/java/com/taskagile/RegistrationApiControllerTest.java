package com.taskagile;


import com.taskagile.domain.application.UserService;
import com.taskagile.domain.model.user.UsernameExistsException;
import com.taskagile.utils.JsonUtils;
import com.taskagile.web.apis.RegistrationApiController;
import com.taskagile.web.payload.RegistrationPayload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationApiController.class)
public class RegistrationApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService serviceMock;

    @Test
    public void register_blankPayload_shouldFailAndReturn400()throws Exception{
        mvc.perform(post("/api/registrations"))
                .andExpect(status().is(400));
    }

    @Test
    public void register_existedUsername_shouldFailndReturn400()throws Exception{
        RegistrationPayload payLoad = new RegistrationPayload();
        payLoad.setUsername("exist");
        payLoad.setEmailAddress("test@taskagile.com");
        payLoad.setPassword("Mypassword");

        doThrow(UsernameExistsException.class)
                .when(serviceMock)
                .register(payLoad.toCommand());

                mvc.perform(
                        post("/api/registrations")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtils.toJson(payLoad))
                        ).andExpect((status().is(400)))
                        .andExpect(jsonPath("$.message").value("Username already exists"));

    }
}