package demo4.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class student implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "s_id")
	private String id;

	@Column(name = "s_name")
	private String name;

	@Column(name = "s_mail")
	private String mail;

	@Column(name = "s_phone")
	private String phone;

	@OneToMany(mappedBy = "dd_student")
	private List<documentDetail> s_documentdetail;

	@OneToMany(mappedBy = "d_student")
	private List<document> s_document;

	

	public student() {
	}

	

	public student(String id, String name, String mail, String phone, List<documentDetail> s_documentdetail,
			List<document> s_document) {
	
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.phone = phone;
		this.s_documentdetail = s_documentdetail;
		this.s_document = s_document;
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

	public List<documentDetail> getS_documentdetail() {
		return s_documentdetail;
	}

	public void setS_documentdetail(List<documentDetail> s_documentdetail) {
		this.s_documentdetail = s_documentdetail;
	}

	public List<document> getS_document() {
		return s_document;
	}

	public void setS_document(List<document> s_document) {
		this.s_document = s_document;
	}



}
