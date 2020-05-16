package dev;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
import dev.repository.SoldeRepo;

/**
 * Code de démarrage de l'application.
 * Insertion de jeux de données.
 */
@Component
public class StartupListener {

	private static final Logger LOGGER = Logger.getLogger(StartupListener.class.getName());
	
	private String appVersion;
    private PasswordEncoder passwordEncoder;
    private CollegueRepo collegueRepo;
    private AbsenceRepo absenceRepo;
    private JourFermeRepo jourFermeRepo;
    private SoldeRepo soldeRepo;
 
    public StartupListener(@Value("${app.version}") String appVersion, PasswordEncoder passwordEncoder, CollegueRepo collegueRepo, 
    		AbsenceRepo absenceRepo, JourFermeRepo jourFermeRepo, SoldeRepo soldeRepo) {
        this.appVersion = appVersion;
        this.passwordEncoder = passwordEncoder;
        this.collegueRepo = collegueRepo;
        this.absenceRepo = absenceRepo;
        this.jourFermeRepo = jourFermeRepo;
        this.soldeRepo = soldeRepo;
        
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onStart() {
   
    	 
    /* ********************************************************************************* */
    	
       // Création d'un administrateur/employe
        Collegue col1 = new Collegue();
        col1.setNom("Admin");
        col1.setPrenom("DEV");
        col1.setEmail("admin@dev.fr");
        col1.setMotDePasse(passwordEncoder.encode("superpass"));
        col1.setRoles(Arrays.asList(new RoleCollegue(col1, Role.ROLE_ADMINISTRATEUR), new RoleCollegue(col1, Role.ROLE_EMPLOYE)));
   
        this.collegueRepo.save(col1);
        
        // Création de ses absences 
        Absence abs1col1 = new Absence(LocalDate.of(2020, 06, 12), LocalDate.of(2020, 06, 15), TypeAbsence.RTT_EMPLOYE, "week-end allongé", Statut.INITIALE, col1);
        Absence abs2col1 = new Absence(LocalDate.of(2020, 11, 02), LocalDate.of(2020, 11, 20), TypeAbsence.CONGES_PAYES, "vacances en Grèce avec Tzatzíki", Statut.INITIALE, col1);
        Absence abs3col1 = new Absence(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 02, 10), TypeAbsence.CONGES_SANS_SOLDE, "tour du monde en vélo", Statut.INITIALE, col1);
        Absence abs4col1 = new Absence(LocalDate.of(2020, 11, 27), LocalDate.of(2021, 01, 8), TypeAbsence.CONGES_PAYES, "vacances de Noël en Islande", Statut.INITIALE, col1);
    
        this.absenceRepo.save(abs1col1);
        this.absenceRepo.save(abs2col1);
        this.absenceRepo.save(abs3col1);
        this.absenceRepo.save(abs4col1);
       
        // Création des deux soldes CP + RTT 
        Solde solde1CP = new Solde(25, TypeSolde.CONGES_PAYES, col1);
        Solde solde1RTT = new Solde(11, TypeSolde.RTT_EMPLOYE, col1);

        this.soldeRepo.save(solde1CP);
        this.soldeRepo.save(solde1RTT);
       
 
    /* ********************************************************************************* */
   	
	   // Création d'un employé
	    Collegue col2 = new Collegue();
	    col2.setNom("User");
	    col2.setPrenom("DEV"); 
	    col2.setEmail("user@dev.fr");
	    col2.setMotDePasse(passwordEncoder.encode("superpass"));
	    col2.setRoles(Arrays.asList(new RoleCollegue(col2, Role.ROLE_EMPLOYE)));
	    
	    this.collegueRepo.save(col2);
	    
	    // Création de ses absences 
	    Absence abs1col2 = new Absence(LocalDate.of(2020, 05, 20), LocalDate.of(2020, 05, 29), TypeAbsence.CONGES_PAYES, "vacances à Djerba", Statut.INITIALE, col2);
	    Absence abs2col2 = new Absence(LocalDate.of(2020, 07, 21), LocalDate.of(2020, 07, 28), TypeAbsence.CONGES_PAYES, "vacances en Italie, à Rome", Statut.INITIALE, col2);
	    Absence abs3col2 = new Absence(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 02, 10), TypeAbsence.CONGES_SANS_SOLDE, "tour du monde en planche à voile", Statut.INITIALE, col2);
	    Absence abs4col2 = new Absence(LocalDate.of(2020, 11, 25), LocalDate.of(2021, 12, 17), TypeAbsence.RTT_EMPLOYE, "", Statut.INITIALE, col2);
	
	    this.absenceRepo.save(abs1col2);
	    this.absenceRepo.save(abs2col2);
	    this.absenceRepo.save(abs3col2);
	    this.absenceRepo.save(abs4col2);
       
	    // Création des deux soldes CP + RTT 
	    Solde solde2CP = new Solde(25, TypeSolde.CONGES_PAYES, col2);
	    Solde solde2RTT = new Solde(11, TypeSolde.RTT_EMPLOYE, col2);

	    this.soldeRepo.save(solde2CP);
   		this.soldeRepo.save(solde2RTT);
   
 
   /* ********************************************************************************* */
   		
        Collegue col3 = new Collegue();
        col3.setNom("Manager");
        col3.setPrenom("DEV");
        col3.setEmail("manager@dev.fr");
        col3.setMotDePasse(passwordEncoder.encode("superpass"));
        col3.setRoles(Arrays.asList(new RoleCollegue(col3, Role.ROLE_MANAGER)));
        
        this.collegueRepo.save(col3);
        
        // Création de ses absences 
	    Absence abs1col3 = new Absence(LocalDate.of(2020, 8, 17), LocalDate.of(2020, 8, 31), TypeAbsence.CONGES_PAYES, "", Statut.INITIALE, col3);
	    Absence abs2col3 = new Absence(LocalDate.of(2020, 7, 15), LocalDate.of(2020, 07, 17), TypeAbsence.RTT_EMPLOYE, "Prolongation de la fête nationale", Statut.INITIALE, col3);
	    Absence abs3col3 = new Absence(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 02, 10), TypeAbsence.CONGES_SANS_SOLDE, "tour du monde en bateau", Statut.INITIALE, col3);
	
	    this.absenceRepo.save(abs1col3);
	    this.absenceRepo.save(abs2col3);
	    this.absenceRepo.save(abs3col3);
       
	    // Création des deux soldes CP + RTT 
	    Solde solde3CP = new Solde(25, TypeSolde.CONGES_PAYES, col3);
	    Solde solde3RTT = new Solde(11, TypeSolde.RTT_EMPLOYE, col3);

	    this.soldeRepo.save(solde3CP);
   		this.soldeRepo.save(solde3RTT);
   		
	/* ********************************************************************************* */
   		
        // -- JOURS FERMES

        JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 5, 21), TypeJourFerme.JOURS_FERIES, "Ascension");
        JourFerme jourFerme2 = new JourFerme(LocalDate.of(2020, 6, 1), TypeJourFerme.JOURS_FERIES, "Pentecôte");
        JourFerme jourFerme3 = new JourFerme(LocalDate.of(2020, 7, 14), TypeJourFerme.JOURS_FERIES, "Fête nationale");
        JourFerme jourFerme4 = new JourFerme(LocalDate.of(2020, 8, 15), TypeJourFerme.JOURS_FERIES, "Assomption");
        JourFerme jourFerme5 = new JourFerme(LocalDate.of(2020, 11, 1), TypeJourFerme.JOURS_FERIES, "Toussaint");
        JourFerme jourFerme6 = new JourFerme(LocalDate.of(2020, 11, 11), TypeJourFerme.JOURS_FERIES, "Armistice");
        JourFerme jourFerme7 = new JourFerme(LocalDate.of(2020, 12, 25), TypeJourFerme.JOURS_FERIES, "Noël");
        JourFerme jourFerme8 = new JourFerme(LocalDate.of(2021, 1, 1), TypeJourFerme.JOURS_FERIES, "Jour de l'an");
        JourFerme jourFerme9 = new JourFerme(LocalDate.of(2021, 4, 5), TypeJourFerme.JOURS_FERIES, "Lundi de Pâques");
        
        
        JourFerme jourFerme10 = new JourFerme(LocalDate.of(2020, 5, 22), TypeJourFerme.RTT_EMPLOYEUR, "Pont de l'ascension");
        JourFerme jourFerme11 = new JourFerme(LocalDate.of(2020, 7, 13), TypeJourFerme.RTT_EMPLOYEUR, "Pont de la fête nationale");
        
        this.jourFermeRepo.save(jourFerme1);
        this.jourFermeRepo.save(jourFerme2);
        this.jourFermeRepo.save(jourFerme3); 
        this.jourFermeRepo.save(jourFerme4);
        this.jourFermeRepo.save(jourFerme5);
        this.jourFermeRepo.save(jourFerme6); 
        this.jourFermeRepo.save(jourFerme7);
        this.jourFermeRepo.save(jourFerme8);
        this.jourFermeRepo.save(jourFerme9);
        this.jourFermeRepo.save(jourFerme10);
        this.jourFermeRepo.save(jourFerme11);
        
    }

}
