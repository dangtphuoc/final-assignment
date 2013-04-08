package springdata.jpa.model;


public class Curriculum extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9114029433117336620L;

	public Curriculum() {
	}
	
	public Curriculum(Long id, String title, String description) {
		super(id, title, description);
	}

}
