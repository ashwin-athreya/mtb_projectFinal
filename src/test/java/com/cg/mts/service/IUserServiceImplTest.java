package com.cg.mts.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.cg.mts.entity.User;
import com.cg.mts.exception.UserCreationError;
import com.cg.mts.repository.UserRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class IUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IUserServiceImpl userService;

    @Test
    void testAddUser() throws UserCreationError {
        when(userRepository.save(any())).thenReturn(getUser());
        User result = userService.addUser(getUser());
        assertEquals("username", result.getUsername());
        assertEquals("CUSTOMER", result.getRole());
    }


    @Test
    void testRemoveUser() {
        User result = userService.removeUser(getUser());
        assertEquals("username", result.getUsername());
        assertEquals("CUSTOMER", result.getRole());
    }

    User getUser(){
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole("CUSTOMER");
        return user;
    }
}