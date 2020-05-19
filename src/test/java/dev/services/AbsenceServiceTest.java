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

/**
 * Tests de joursOuvresEntreDeuxDates du service de l'entité Absence
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@ExtendWith(MockitoExtension.class)
public class AbsenceServiceTest {
	
	@Mock
	JourFermeRepo jourFermeRepository;
	
	@InjectMocks
	AbsenceService absenceService;

	
	/* ****************** TESTS joursOuvresEntreDeuxDates ****************************** */
	
	/**
	 * Test d'une absence commençant un lundi
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest1() {
		
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 07, 6);
		LocalDate dateFin = LocalDate.of(2020, 07, 24);
		
		assertEquals(15, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
		
	}
	
	
	/**
	 * Test d'une absence commençant un mardi
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest2() {
		
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 07, 7);
		LocalDate dateFin = LocalDate.of(2020, 07, 24);
		
		assertEquals(14, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
		
	}
	
	/**
	 * Test d'une absence commençant un mercredi
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest3() {
		
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 07, 8);
		LocalDate dateFin = LocalDate.of(2020, 07, 24);
		
		assertEquals(13, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
		
	}
	
	/**
	 * Test d'une absence commençant un jeudi
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest4() {
		
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 07, 9);
		LocalDate dateFin = LocalDate.of(2020, 07, 24);
		
		assertEquals(12, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
		
	}
	
	/**
	 * Test d'une absence commençant un vendredi
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest5() {
		
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 07, 10);
		LocalDate dateFin = LocalDate.of(2020, 07, 24);
		
		assertEquals(11, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
		
	}
	
	/**
	 * Test d'une absence avec l'ajout d'un jour férié et d'un RTT employeur 
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest6() {
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
	
	/**
	 * Test d'une absence avec un jour férié le week-end
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest7() {
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
	
	/**
	 * Test d'une absence de courte durée
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest8() {
    
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 04, 6);
		LocalDate dateFin = LocalDate.of(2020, 04, 9);
		
		assertEquals(4, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	/**
	 * Test d'une absence de longue durée 
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest9() {
		   
		when(jourFermeRepository.findAll()).thenReturn(new ArrayList<JourFerme>());
		
		LocalDate dateDebut = LocalDate.of(2020, 6, 5);
		LocalDate dateFin = LocalDate.of(2020, 8, 14);
		
		assertEquals(51, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	/**
	 * Test d'une absence commençant par un jour férié 
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest10() {
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
	
	/**
	 * Test d'une absence commençant par un RTT employeur 
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest11() {
        JourFerme jourFerme2 = new JourFerme(LocalDate.of(2020, 5, 22), TypeJourFerme.RTT_EMPLOYEUR, "");
        
        List<JourFerme> listeJoursFermes = new ArrayList<>();
        listeJoursFermes.add(jourFerme2);
    
		when(jourFermeRepository.findAll()).thenReturn(listeJoursFermes);
		
		LocalDate dateDebut = LocalDate.of(2020, 5, 22);
		LocalDate dateFin = LocalDate.of(2020, 5, 25);
		
		assertEquals(1, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	/**
	 * Test d'une absence terminant par un jour férié
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest12() {
		JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 5, 21), TypeJourFerme.JOURS_FERIES, "");
        
        List<JourFerme> listeJoursFermes = new ArrayList<>();
        listeJoursFermes.add(jourFerme1);
    
		when(jourFermeRepository.findAll()).thenReturn(listeJoursFermes);
		
		LocalDate dateDebut = LocalDate.of(2020, 5, 19);
		LocalDate dateFin = LocalDate.of(2020, 5, 21);
		
		assertEquals(2, absenceService.joursOuvresEntreDeuxDates(dateDebut, dateFin));
	}
	
	/**
	 * Test d'une absence terminant par un RTT employeur
	 */
	@Test
	void joursOuvresEntreDeuxDatesTest13() {
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
