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
   
    	 
       // Création de deux utilisateurs

        Collegue col1 = new Collegue();
        col1.setNom("Admin");
        col1.setPrenom("DEV");
        col1.setEmail("admin@dev.fr");
        col1.setMotDePasse(passwordEncoder.encode("superpass"));
        col1.setRoles(Arrays.asList(new RoleCollegue(col1, Role.ROLE_ADMINISTRATEUR), new RoleCollegue(col1, Role.ROLE_EMPLOYE)));
        
        // TESTS POUR UN COLLEGUE (recup des absences et des soldes)
         
      //creation d'une absence *********
        JourFerme jourFerme1 = new JourFerme(LocalDate.of(2020, 06, 12), TypeJourFerme.JOURS_FERIES, "Jour ferme numero 1");
        JourFerme jourFerme2 = new JourFerme(LocalDate.of(2020, 11, 02), TypeJourFerme.RTT_EMPLOYEUR, "Jour ferme numero 2");
        JourFerme jourFerme3 = new JourFerme(LocalDate.of(2020, 07, 12), TypeJourFerme.JOURS_FERIES, "Jour ferme numero 3");

        //creation d'une absence *********
        Absence abs1col1 = new Absence(LocalDate.of(2020, 06, 12), LocalDate.of(2020, 06, 15), TypeAbsence.CONGES_PAYES, "vacances au soleil", Statut.EN_ATTENTE_VALIDATION, col1);
        Absence abs2col1 = new Absence(LocalDate.of(2020, 11, 02), LocalDate.of(2020, 11, 20), TypeAbsence.CONGES_PAYES, "vacances au soleil avec bluelagoon", Statut.INITIALE, col1);
        Absence abs3col1 = new Absence(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 02, 10), TypeAbsence.CONGES_PAYES, "tour du monde en vélo, avec les petites roues", Statut.REJETEE, col1);
        Absence abs4col1 = new Absence(LocalDate.of(2020, 11, 27), LocalDate.of(2021, 01, 8), TypeAbsence.CONGES_PAYES, "Vacances de Noël en Islande", Statut.INITIALE, col1);
        
        List<Absence> listeAbsences = new ArrayList<>();
        listeAbsences.add(abs1col1);
        listeAbsences.add(abs2col1); 
        col1.setAbsences(listeAbsences);
        
        //System.out.println("listeAbsences[0] = " + listeAbsences.get(0).getDateDebut());
        //System.out.println("listeAbsences[1] = " + listeAbsences.get(1).getDateDebut());
       
        // ****************************************
        
        // création des deux soldes CP + RTT *********
       Solde solde1CP = new Solde(25, TypeSolde.CONGES_PAYES, col1);
       Solde solde1RTT = new Solde(11, TypeSolde.RTT_EMPLOYE, col1);
       
       List<Solde> listeSoldesCol1 = new ArrayList<>();
       listeSoldesCol1.add(solde1RTT);
       listeSoldesCol1.add(solde1CP);
       
       col1.setSoldes(listeSoldesCol1);
       //************************************************* 
   
       this.collegueRepo.save(col1);
        
        this.soldeRepo.save(solde1CP);
        this.soldeRepo.save(solde1RTT);
        this.absenceRepo.save(abs1col1);
        this.absenceRepo.save(abs2col1);
        this.absenceRepo.save(abs3col1);
        this.absenceRepo.save(abs4col1);
        // -- JOUR FERME

        this.jourFermeRepo.save(jourFerme1);
        this.jourFermeRepo.save(jourFerme2);
        this.jourFermeRepo.save(jourFerme3); 
        
      // System.out.println(abs1col1.getId());
        
        Collegue col2 = new Collegue();
        col2.setNom("User");
        col2.setPrenom("DEV"); 
        col2.setEmail("user@dev.fr");
        col2.setMotDePasse(passwordEncoder.encode("superpass"));
        col2.setRoles(Arrays.asList(new RoleCollegue(col2, Role.ROLE_EMPLOYE)));
        this.collegueRepo.save(col2);
        
        Collegue col3 = new Collegue();
        col3.setNom("Manager");
        col3.setPrenom("DEV");
        col3.setEmail("manager@dev.fr");
        col3.setMotDePasse(passwordEncoder.encode("superpass"));
        col3.setRoles(Arrays.asList(new RoleCollegue(col3, Role.ROLE_MANAGER)));
        this.collegueRepo.save(col3);
    }

}
