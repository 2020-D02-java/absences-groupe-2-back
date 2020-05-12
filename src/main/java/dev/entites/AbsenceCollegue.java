package dev.entites;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="absence")
public class AbsenceCollegue {


	
	/** id de l'absence**/
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	/** collegue auquel l'absence est associée **/
	@ManyToOne
    @JoinColumn(name = "collegue_id")
    private Collegue collegue;

	@Embedded
	private Absence absence;

	public AbsenceCollegue() {
		
	}
	
	/** Constructeur
	 *
	 * @param collegue
	 * @param absence
	 */
	public AbsenceCollegue(Collegue collegue, Absence absence) {
		super();
		this.collegue = collegue;
		this.absence = absence;
	}

	/** Getter
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/** Setter
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/** Getter
	 *
	 * @return the collegue
	 */
	public Collegue getCollegue() {
		return collegue;
	}

	/** Setter
	 *
	 * @param collegue the collegue to set
	 */
	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}

	/** Getter
	 *
	 * @return the absence
	 */
	public Absence getAbsence() {
		return absence;
	}

	/** Setter
	 *
	 * @param absence the absence to set
	 */
	public void setAbsence(Absence absence) {
		this.absence = absence;
	}

}
