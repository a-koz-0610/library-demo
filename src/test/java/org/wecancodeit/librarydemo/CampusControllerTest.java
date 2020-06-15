package org.wecancodeit.librarydemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.librarydemo.controllers.CampusController;
import org.wecancodeit.librarydemo.models.Campus;
import org.wecancodeit.librarydemo.repositories.CampusRepository;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CampusControllerTest {

    @InjectMocks
    private CampusController underTest;

    @Mock
    private CampusRepository campusRepo;

    @Mock
    private Campus campus1;
    @Mock
    private Campus campus2;

    @Mock
    private Model mockModel;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAllCampuses(){
        Collection<Campus> allCampuses = Arrays.asList(campus1, campus2);
        when(campusRepo.findAll()).thenReturn(allCampuses);
        underTest.displayCampuses(mockModel);
        verify(mockModel).addAttribute("campuses", allCampuses);
    }

    @Test
    public void shouldAddANewCampus(){
        underTest.addCampus("Cincinnati");
        verify(campusRepo).save(new Campus("Cincinnati"));
    }

    @Test
    public void shouldAddCampusAndRedirectToCampusesEndPoint(){
        String result = underTest.addCampus("Cincinnati");
        assertThat(result).isEqualTo("redirect:campuses");
    }

}
