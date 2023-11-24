package com.cg.mts.controller;

import com.cg.mts.entity.Login;
import com.cg.mts.entity.User;
import com.cg.mts.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void loginUserTest() throws Exception {
        when(loginService.loginWithData(any(), any())).thenReturn(createMockLogin());
        mvc.perform(post("/login/{username}/{password}", "testUser", "testPassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void loginUserTestForException() throws Exception {
        mvc.perform(post("/login/{username}/{password}", "testUser", "testPassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void logOutTest() throws Exception {
        when(loginService.loginStatus()).thenReturn(true);
        mvc.perform(post("/logout") .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Login createMockLogin() {
        Login login = new Login();
        User user = new User();
        user.setUsername("user");
        user.setUserid(12);
        login.setUser(user);
        login.setLoginStatus(true);
        return login;
    }
}
