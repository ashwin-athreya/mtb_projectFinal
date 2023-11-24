package com.cg.mts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import com.cg.mts.entity.Screen;
import com.cg.mts.entity.Theatre;
import com.cg.mts.exception.ScreenNotFoundException;
import com.cg.mts.repository.ScreenRepository;
import com.cg.mts.repository.TheatreRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ScreenServiceImplTest {

    @Mock
    private ScreenRepository screenRepository;

    @Mock
    private TheatreRepository theatreRepository;

    @InjectMocks
    private ScreenServiceImpl screenService;

    @Test
    void testAddScreen() throws ScreenNotFoundException {
        when(theatreRepository.getOne(Mockito.any())).thenReturn(getTestTheatre());
        when(screenRepository.save(any(Screen.class))).thenReturn(getTestScreen());

        Screen result = screenService.addScreen(getTestScreen(), 1);
        assertEquals(getTestTheatre().getTheatreId(), result.getTheatre().getTheatreId());
    }

    @Test
    void testAddScreenScreenExists() {
        when(screenRepository.existsById(Mockito.any())).thenReturn(true);
        assertThrows(ScreenNotFoundException.class, () -> screenService.addScreen(getTestScreen(), 1));
    }

    @Test
    void testViewScreenList() {
        // Mock the behavior of screenRepository.findAll() to return an empty list
        when(screenRepository.findAll()).thenReturn(Collections.emptyList());

        // Use assertThrows to verify that the ScreenNotFoundException is thrown
        ScreenNotFoundException exception = assertThrows(ScreenNotFoundException.class, () -> {
            screenService.viewScreenList();
        });

        
        assertEquals("No screens found", exception.getMessage());

        verify(screenRepository, times(1)).findAll();
    }

    @Test
    void testViewScreenListWithScreens() throws ScreenNotFoundException {
        
        Screen testScreen = getTestScreen();
        when(screenRepository.findAll()).thenReturn(List.of(testScreen));

        List<Screen> result = screenService.viewScreenList();

        assertEquals(testScreen.getScreenId(), result.get(0).getScreenId());

        verify(screenRepository, times(1)).findAll();
    }

    @Test
    void testUpdateScreen() {
        when(theatreRepository.getOne(Mockito.any())).thenReturn(getTestTheatre());
        when(screenRepository.save(any(Screen.class))).thenReturn(getTestScreen());
        Screen result = screenService.updateScreen(getTestScreen(), 1);
        assertEquals(getTestTheatre().getTheatreId(), result.getTheatre().getTheatreId());
        verify(theatreRepository, times(1)).getOne(1);
        verify(screenRepository, times(1)).save(any(Screen.class));
    }

    @Test
    void testGetTheatre() throws ScreenNotFoundException {
        when(screenRepository.findById(Mockito.any())).thenReturn(Optional.of(getTestScreen()));
        Theatre result = screenService.getTheatre(getTestScreen().getScreenId());
        assertEquals(getTestTheatre(), result);
        verify(screenRepository, times(1)).findById(getTestScreen().getScreenId());
    }

    @Test
    void testGetTheatreScreenNotFound() {
        when(screenRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> screenService.getTheatre(1));
    }
    
    Theatre getTestTheatre(){
        Theatre theatre = new Theatre();
        theatre.setTheatreId(1);
        theatre.setTheatreName("Theatre1");
        return theatre;
    }

    Screen getTestScreen(){
        Screen screen = new Screen();
        screen.setScreenId(1);
        screen.setScreenName("Screen1");
        screen.setTheatre(getTestTheatre());
        return screen;
    }
}
