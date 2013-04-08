package springdata.jpa.model;


public class Location extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4846187201625563470L;
	public Location() {
		
	}
	public Location(Long id, String title, String description) {
		super(id, title, description);
	}
}
