package springdata.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	protected Long id;
	
	@Column(name="Title")
	protected String title;
	
	@Column(name="Description")
	protected String description;
	
	public AbstractEntity() {
	}
	
	public AbstractEntity(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public AbstractEntity(Long id, String title, String description) {
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
		
		if(!(obj instanceof AbstractEntity)) return false;
		
		AbstractEntity otherBean = (AbstractEntity) obj;
		if(this.getId() == null || otherBean.getId() == null) return false;
		
		return this.getId() == otherBean.getId();
	}
	
	@Override
	public int hashCode() {
		if(this.getId() != null) return getId().hashCode();
		else return this.getTitle().hashCode();
	}
}
