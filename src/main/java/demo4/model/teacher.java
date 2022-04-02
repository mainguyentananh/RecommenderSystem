package demo4.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teacher")
public class teacher implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "t_id")
	private String id;

	@Column(name = "t_name")
	private String name;

	@Column(name = "t_mail")
	private String mail;

	@Column(name = "t_phone")
	private String phone;

	@Column(name = "t_degree")
	private String degree;

	@Column(name = "t_image")
	private String image;

	@Column(name = "t_note")
	private String note;
	
	@OneToMany(mappedBy = "d_teacher")
	private List<document> t_document;

	@OneToMany(mappedBy = "te_teacher")
	private List<teach> t_teach;

	public teacher() {
	}

	public teacher(String id, String name, String mail, String phone, String degree, String image, String note,
			List<document> t_document, List<teach> t_teach) {
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.degree = degree;
		this.image = image;
		this.note = note;
		this.t_document = t_document;
		this.t_teach = t_teach;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<document> getT_document() {
		return t_document;
	}

	public void setT_document(List<document> t_document) {
		this.t_document = t_document;
	}

	public List<teach> getT_teach() {
		return t_teach;
	}

	public void setT_teach(List<teach> t_teach) {
		this.t_teach = t_teach;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
