package dev.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.entites.JourFerme;
import dev.entites.TypeJourFerme;
import dev.repository.JourFermeRepo;

@ExtendWith(MockitoExtension.class)
public class AbsenceServiceTest {
	
	@Mock
	JourFermeRepo jourFermeRepository;
	
	@InjectMocks
	AbsenceService absenceService;

	
	/* ****************** TESTS joursOuvresEntreDeuxDates ****************************** */
	
	@Test
	void joursOuvresEntreDeuxDatesTest1() {
		
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 04, 8);
		LocalDate dateFin = LocalDate.of(2020, 04, 23);
		
		assertEquals(12, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
		
	}
	
	@Test
	void joursOuvresEntreDeuxDatesTest2() {
		JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 4, 9), TypeJourFerme.JOURS_FERIES, "");
        JourFerme jourFerme2 = new JourFerme(LocalDate.of(2020, 4, 21), TypeJourFerme.RTT_EMPLOYEUR, "");
        
        List<JourFerme> listeJoursFermes = new ArrayList<>();
        listeJoursFermes.add(jourFerme1);
        listeJoursFermes.add(jourFerme2);
    
		when(jourFermeRepository.findAll()).thenReturn(listeJoursFermes);
		
		LocalDate dateDebut = LocalDate.of(2020, 04, 8);
		LocalDate dateFin = LocalDate.of(2020, 04, 23);
		
		assertEquals(10, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	@Test
	void joursOuvresEntreDeuxDatesTest3() {
		JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 4, 11), TypeJourFerme.JOURS_FERIES, "");
        JourFerme jourFerme2 = new JourFerme(LocalDate.of(2020, 4, 21), TypeJourFerme.RTT_EMPLOYEUR, "");
        
        List<JourFerme> listeJoursFermes = new ArrayList<>();
        listeJoursFermes.add(jourFerme1);
        listeJoursFermes.add(jourFerme2);
    
		when(jourFermeRepository.findAll()).thenReturn(listeJoursFermes);
		
		LocalDate dateDebut = LocalDate.of(2020, 04, 8);
		LocalDate dateFin = LocalDate.of(2020, 04, 23);
		
		assertEquals(11, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	@Test
	void joursOuvresEntreDeuxDatesTest4() {
		JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 4, 7), TypeJourFerme.JOURS_FERIES, "");
        
        List<JourFerme> listeJoursFermes = new ArrayList<>();
        listeJoursFermes.add(jourFerme1);
    
		when(jourFermeRepository.findAll()).thenReturn(listeJoursFermes);
		
		LocalDate dateDebut = LocalDate.of(2020, 04, 6);
		LocalDate dateFin = LocalDate.of(2020, 04, 9);
		
		assertEquals(3, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	@Test
	void joursOuvresEntreDeuxDatesTest5() {
		   
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 6, 01);
		LocalDate dateFin = LocalDate.of(2020, 8, 14);
		
		assertEquals(55, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	@Test
	void joursOuvresEntreDeuxDatesTest6() {
		JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 5, 21), TypeJourFerme.JOURS_FERIES, "");
        JourFerme jourFerme2 = new JourFerme(LocalDate.of(2020, 5, 22), TypeJourFerme.RTT_EMPLOYEUR, "");
        
        List<JourFerme> listeJoursFermes = new ArrayList<>();
        listeJoursFermes.add(jourFerme1);
        listeJoursFermes.add(jourFerme2);
    
		when(jourFermeRepository.findAll()).thenReturn(listeJoursFermes);
		
		LocalDate dateDebut = LocalDate.of(2020, 5, 21);
		LocalDate dateFin = LocalDate.of(2020, 5, 25);
		
		assertEquals(1, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	

	@Test
	void joursOuvresEntreDeuxDatesTest7() {
		JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 5, 21), TypeJourFerme.JOURS_FERIES, "");
        JourFerme jourFerme2 = new JourFerme(LocalDate.of(2020, 5, 22), TypeJourFerme.RTT_EMPLOYEUR, "");
        
        List<JourFerme> listeJoursFermes = new ArrayList<>();
        listeJoursFermes.add(jourFerme1);
        listeJoursFermes.add(jourFerme2);
    
		when(jourFermeRepository.findAll()).thenReturn(listeJoursFermes);
		
		LocalDate dateDebut = LocalDate.of(2020, 5, 19);
		LocalDate dateFin = LocalDate.of(2020, 5, 22);
		
		assertEquals(2, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
}
