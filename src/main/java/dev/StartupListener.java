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
import dev.entites.Role;
import dev.entites.RoleCollegue;
import dev.entites.Solde;
import dev.entites.Statut;
import dev.entites.TypeAbsence;
import dev.entites.TypeSolde;
import dev.repository.CollegueRepo;

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
 
    public StartupListener(@Value("${app.version}") String appVersion, PasswordEncoder passwordEncoder, CollegueRepo collegueRepo) {
        this.appVersion = appVersion;
        this.passwordEncoder = passwordEncoder;
        this.collegueRepo = collegueRepo;
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
        Absence abs1col1 = new Absence(LocalDate.of(2020, 02, 04), LocalDate.of(2020, 02, 14), TypeAbsence.CONGES_PAYES, "vacances au soleil", Statut.EN_ATTENTE_VALIDATION, col1);
        Absence abs2col1 = new Absence(LocalDate.of(2020, 07, 15), LocalDate.of(2020, 8, 02), TypeAbsence.CONGES_PAYES, "vacances au soleil avec bluelagoon", Statut.INITIALE, col1);
        
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
