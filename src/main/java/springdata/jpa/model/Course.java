package springdata.jpa.model;


public class Course extends AbstractEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1159175552691243899L;
	

	public Course() {
	}
	
	public Course (Long id, String title, String description) {
		super(id, title, description);
	}
}
