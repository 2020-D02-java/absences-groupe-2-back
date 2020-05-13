package dev.entites;

import javax.persistence.*;

/** Représentation du role d'un collègue
 *
 * @author KOMINIARZ Anaïs
 *
 */
@Entity
public class RoleCollegue {

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

    public RoleCollegue() {
    }

    public RoleCollegue(Collegue collegue, Role role) {
        this.collegue = collegue;
        this.role = role;
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

	/** Getter
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/** Setter
	 *
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
}
