package dev.entites;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Représentation du role d'un collègue
 * 
 * @author KOMINIARZ Anaïs, SAGAN Jonathan, BATIGNES Pierre, GIRARD Vincent.
 *
 */
@Entity
public class RoleCollegue {

	// Déclarations
	/** id du role collegue **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/** collegue **/
	@ManyToOne
	@JoinColumn(name = "collegue_id")
	private Collegue collegue;

	/** role d'un collegue **/
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * Constructeur
	 * 
	 * @param collegue
	 * @param role
	 */
	public RoleCollegue(Collegue collegue, Role role) {
		this.collegue = collegue;
		this.role = role;
	}

	/**
	 * Constructeur vide
	 */
	public RoleCollegue() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Long id) {
	}

	public Collegue getCollegue() {
		return collegue;
	}

	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}

	/**
	 * Getter
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Setter
	 *
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
}
