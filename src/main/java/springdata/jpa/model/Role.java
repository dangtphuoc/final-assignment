package springdata.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Tbl_Role")
public class Role extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1165646170666735994L;

	public Role() {
	}
	
	public Role(Long id, String title, String description) {
		super(id, title, description);
	}
	
}
