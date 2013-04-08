package springdata.jpa.dto;


public class BaseDTO{

	protected Long id;
	
	protected String title;
	
	protected String description;
	
	public BaseDTO() {
	}
	
	public BaseDTO(Long id) {
		this.id = id;
	}
	
	public BaseDTO(Long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj.getClass() != this.getClass()) return false;
		
		if(!(obj instanceof BaseDTO)) return false;
		
		BaseDTO otherBean = (BaseDTO) obj;
		if(this.getId() == null || otherBean.getId() == null) return false;
		
		return this.getId() == otherBean.getId();
	}
	
	@Override
	public int hashCode() {
		if(this.getId() != null) return getId().hashCode();
		else return this.getTitle().hashCode();
	}
}
