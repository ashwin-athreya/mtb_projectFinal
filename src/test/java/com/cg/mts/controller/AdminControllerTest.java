package com.cg.mts.controller;

import com.cg.mts.dto.ScreenDTO;
import com.cg.mts.dto.UserDTO;
import com.cg.mts.entity.Screen;
import com.cg.mts.entity.Theatre;
import com.cg.mts.entity.User;
import com.cg.mts.service.IAdminService;
import com.cg.mts.service.IUserService;
import com.cg.mts.service.ScreenService;
import com.cg.mts.service.TheatreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.TestUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
	

    @Mock
    private ScreenService screenService;
    
    @Mock
    private IUserService userService;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private IAdminService adminService;
    
    @Mock
    private TheatreService theatreService;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void registerAdminTest() throws Exception {
        mvc.perform(post("/admin/registeradmin/{username}/{password}", "user", "123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void removeAdminTest() throws Exception {
        mvc.perform(delete("/admin/removeadmin/{adminId}",122)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void removeUserTest() throws Exception {
        when(userService.removeUser(any(User.class))).thenReturn(new User());
        mvc.perform(delete("/admin/removeuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getUserDTO())))
                .andExpect(status().isOk());
    }

    @Test
    void addAScreenTest() throws Exception {
        // Arrange
        ScreenDTO mockScreenDTO = createMockScreenDTO();

        // Mock the behavior of theatreservice.findTheatres to return a non-null Theatre object
        when(theatreService.findTheatres(any(Integer.class))).thenReturn(createMockTheatre());

        // Mock the behavior of screenService.addScreen to return a non-null Screen object
        when(screenService.addScreen(any(Screen.class), any(Integer.class))).thenReturn(createMockScreen());

        // Act
        ResultActions resultActions = mvc.perform(post("/admin/screens/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockScreenDTO))
                .param("theatreId", "1"));

        // Assert
        resultActions.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print()); // Add this line for better error reporting
    }

    
    @Test
    void updateScreenTest() throws Exception {
        when(screenService.updateScreen(any(), any())).thenReturn(createMockScreen());
        mvc.perform(put("/admin/screens/update/{theatreId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createMockScreenDTO())))
                         .andExpect(status().isOk());
    }
    
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ScreenDTO createMockScreenDTO() {
        ScreenDTO screen = new ScreenDTO();
        screen.setScreenId(1);
        screen.setRows(3);
        screen.setColumns(2);
        screen.setScreenName("Screen1");
        return screen;
    }

    private Screen createMockScreen() {
        Screen screen = new Screen();
        screen.setScreenId(1);
        screen.setRows(3);
        screen.setColumns(2);
        screen.setScreenName("Screen1");
        screen.setTheatre(createMockTheatre());
        return screen;
    }
    
    UserDTO getUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("john.doe");
        userDTO.setPassword("password123");
        userDTO.setRole("CUSTOMER");
        return userDTO;
    }
    
    private Theatre createMockTheatre() {
        Theatre theatre = new Theatre();
        theatre.setTheatreId(1); // Set the expected theatreId
        theatre.setTheatreName("MockTheatre"); // Set the expected theatreName
        theatre.setTheatreCity("MockCity"); // Set the expected theatreCity


        return theatre;
    }
}
