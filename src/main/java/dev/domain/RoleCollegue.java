package dev.domain;

import javax.persistence.*;

@Entity
public class RoleCollegue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collegue_id")
    private Collegue collegue;

    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleCollegue() {
    }

    public RoleCollegue(Collegue collegue, Role role) {
        this.collegue = collegue;
        this.role = role;
    }

    public Long getId() {
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
