package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.cg.mts.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.cg.mts.entity.Login;
import com.cg.mts.entity.User;
import com.cg.mts.repoImpl.QueryClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class LoginServiceTest {

    @Mock
    private QueryClass queryClass;

    @InjectMocks
    private LoginService loginService;

    @Test
    void testLoginWithData() throws Exception {
        when(queryClass.findByUserName(Mockito.any(), Mockito.any())).thenReturn(getUser());

        Login login = loginService.loginWithData("username", "password");
        assertTrue(login.isLoginStatus());
    }

    @Test
    void testLoginWithDataInvalidPassword() throws UserNotFoundException {
        when(queryClass.findByUserName(Mockito.any(), Mockito.any())).thenReturn(getUser());
        assertThrows(Exception.class, () -> loginService.loginWithData("username", "123"));
    }

    @Test
    void testLogoutPresentUser() throws Exception {
        when(queryClass.findByUserName(Mockito.any(), Mockito.any())).thenReturn(getUser());
        loginService.loginWithData("username", "password");

        assertFalse(loginService.logoutPresentUser().isLoginStatus());
    }

    @Test
    void testLoginStatus() throws Exception {
        when(queryClass.findByUserName(Mockito.any(), Mockito.any())).thenReturn(getUser());

        loginService.loginWithData("username", "password");
        assertTrue(loginService.loginStatus());
    }

    @Test
    void testGetRole() throws Exception {
        when(queryClass.findByUserName(Mockito.any(), Mockito.any())).thenReturn(getUser());

        loginService.loginWithData("username", "password");

        assertEquals("USER", loginService.getRole());
    }

    User getUser(){
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole("USER");
        return user;
    }
}
