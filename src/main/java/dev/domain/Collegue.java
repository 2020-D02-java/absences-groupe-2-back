package dev.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Collegue {

    /** id du collegue **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** nom du collegue **/
    private String nom;

    /** prenom du collegue **/
    private String prenom;

    /** email du collegue **/
    private String email;

    /** mot de passe du collegue **/
    private String motDePasse;

    /** roles du collegue **/
    @OneToMany(mappedBy = "collegue", cascade = CascadeType.PERSIST)
    private List<RoleCollegue> roles;
    
    /** soldes du collegue **/
    @OneToMany(mappedBy = "collegue", cascade = CascadeType.PERSIST)
	private List<Solde> soldes;
    
    
    /** manager de plusieurs collegues **/
    private Collegue manager; 
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<RoleCollegue> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleCollegue> roles) {
        this.roles = roles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
