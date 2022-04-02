package demo4.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "classroom")
public class classroom implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cr_id")
	private String id;

	@Column(name = "cr_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "cr_category_id")
	private category cr_category;

	@OneToMany(mappedBy = "te_classroom")
	private List<teach> cr_teach;

	@OneToMany(mappedBy = "dd_classroom")
	private List<documentDetail> cr_documentdetail;

	public classroom() {
	}

	public classroom(String id, String name, category cr_category, List<teach> cr_teach,
			List<documentDetail> cr_documentdetail) {
		this.id = id;
		this.name = name;
		this.cr_category = cr_category;
		this.cr_teach = cr_teach;
		this.cr_documentdetail = cr_documentdetail;
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

	public category getCr_category() {
		return cr_category;
	}

	public void setCr_category(category cr_category) {
		this.cr_category = cr_category;
	}

	public List<documentDetail> getCr_documentdetail() {
		return cr_documentdetail;
	}

	public void setCr_documentdetail(List<documentDetail> cr_documentdetail) {
		this.cr_documentdetail = cr_documentdetail;
	}

	public List<teach> getCr_teach() {
		return cr_teach;
	}

	public void setCr_teach(List<teach> cr_teach) {
		this.cr_teach = cr_teach;
	}

}
