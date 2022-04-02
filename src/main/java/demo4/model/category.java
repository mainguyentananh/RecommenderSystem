package demo4.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class category implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "c_id")
	private String id;
	
	@Column(name ="c_name")
	private String name;
	
	@OneToMany(mappedBy = "cr_category")
	private List<classroom> c_classrom;

	@OneToMany(mappedBy = "d_category")
	private List<document> c_document;

	public category() {
	}

	public category(String id, String name, List<classroom> c_classrom, List<document> c_document) {
		this.id = id;
		this.name = name;
		this.c_classrom = c_classrom;
		this.c_document = c_document;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<classroom> getC_classrom() {
		return c_classrom;
	}

	public void setC_classrom(List<classroom> c_classrom) {
		this.c_classrom = c_classrom;
	}

	public List<document> getC_document() {
		return c_document;
	}

	public void setC_document(List<document> c_document) {
		this.c_document = c_document;
	}
	
	
	
}
