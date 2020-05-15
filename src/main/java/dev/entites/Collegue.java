package dev.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.criteria.Fetch;

/**
 * Entité collegue
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Entity
public class Collegue {

	// Déclarations
    /** id du collegue **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    
    
    /** absences du collegue **/
    @OneToMany(mappedBy = "collegue", cascade = CascadeType.PERSIST)
	private List<Absence> absences;
    
    
    /** manager de plusieurs collegues **/
    @ManyToOne
    private Collegue manager; 
    
    /** subordonnes d'un manager **/
    @OneToMany(mappedBy = "manager")
    List<Collegue> subordonnes;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

	/** Getter
	 *
	 * @return the soldes
	 */
	public List<Solde> getSoldes() {
		return soldes;
	}

	/** Setter
	 *
	 * @param soldes the soldes to set
	 */
	public void setSoldes(List<Solde> soldes) {
		this.soldes = soldes;
	}

	/** Getter
	 *
	 * @return the absences
	 */
	public List<Absence> getAbsences() {
		return absences;
	}

	/** Setter
	 *
	 * @param absences the absences to set
	 */
	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	/** Getter
	 *
	 * @return the manager
	 */
	public Collegue getManager() {
		return manager;
	}

	/** Setter
	 *
	 * @param manager the manager to set
	 */
	public void setManager(Collegue manager) {
		this.manager = manager;
	}

	/** Getter
	 *
	 * @return the subordonnes
	 */
	public List<Collegue> getSubordonnes() {
		return subordonnes;
	}

	/** Setter
	 *
	 * @param subordonnes the subordonnes to set
	 */
	public void setSubordonnes(List<Collegue> subordonnes) {
		this.subordonnes = subordonnes;
	}
}
