package dev.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.entites.Absence;
import dev.entites.Collegue;
import dev.entites.JourFerme;
import dev.entites.Role;
import dev.entites.RoleCollegue;
import dev.entites.Solde;
import dev.entites.Statut;
import dev.entites.TypeAbsence;
import dev.entites.TypeJourFerme;
import dev.entites.TypeSolde;
import dev.repository.AbsenceRepo;
import dev.repository.CollegueRepo;
import dev.repository.JourFermeRepo;

@SpringBootTest
public class AbsenceServiceTest {
	
	@MockBean
	JourFermeRepo jourFermeRepository;
	
	@MockBean
	CollegueRepo collegueRepository;
	
	@MockBean
	AbsenceRepo absenceRepository;
	
	@Autowired 
	AbsenceService absenceService;
	
	Collegue collegueTest;
	

	@BeforeEach
	public void init() {
		
		// Initialisation d'un collègue
		List<RoleCollegue> roles = new ArrayList<>();
		RoleCollegue roleCollegue = new RoleCollegue(collegueTest, Role.ROLE_ADMINISTRATEUR);
		roles.add(roleCollegue);
		
		List<Solde> soldes = new ArrayList<>();
		Solde solde1 = new Solde(25, TypeSolde.CONGES_PAYES, collegueTest);
		Solde solde2 = new Solde(11, TypeSolde.RTT_EMPLOYE, collegueTest);
		soldes.add(solde1);
		soldes.add(solde2);
		
		List<Absence> absences = new ArrayList<>();
		Absence abs1 = new Absence(LocalDate.of(2020, 06, 12), LocalDate.of(2020, 06, 15), TypeAbsence.RTT_EMPLOYE, "week-end prolongé", Statut.INITIALE, collegueTest);
	    Absence abs2 = new Absence(LocalDate.of(2020, 11, 02), LocalDate.of(2020, 11, 20), TypeAbsence.CONGES_PAYES, "vacances en Grèce avec Tzatzíki", Statut.INITIALE, collegueTest);
	    Absence abs3 = new Absence(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 02, 10), TypeAbsence.CONGES_SANS_SOLDE, "tour du monde en vélo", Statut.INITIALE, collegueTest);
		
	    absences.add(abs1);
	    absences.add(abs2);
	    absences.add(abs3);
	    
		collegueTest = new Collegue("testnom", "testprenom", "testnom.testprenom@gmail.com", roles, soldes, absences);
		
	}
	
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
	
	/* ****************** TESTS traitementDeNuit ****************************** */
	
	void traitementDeNuit1() {
		
		JourFerme rtt1 = new JourFerme(LocalDate.of(2020, 5, 21), TypeJourFerme.RTT_EMPLOYEUR, "");
        JourFerme rtt2 = new JourFerme(LocalDate.of(2020, 5, 22), TypeJourFerme.RTT_EMPLOYEUR, "");
        
        List<JourFerme> listeRtt = new ArrayList<>();
        listeRtt.add(rtt1);
        listeRtt.add(rtt2);
		
		when(jourFermeRepository.findByType(TypeJourFerme.RTT_EMPLOYEUR)).thenReturn(Optional.of(listeRtt));
		
		
		
	}
	
}
