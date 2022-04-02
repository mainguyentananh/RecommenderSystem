package demo4.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "document")
public class document implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "d_id")
	private int id;

	@Column(name = "d_council1")
	private String council1;

	@Column(name = "d_council2")
	private String council2;

	@Column(name = "d_council3")
	private String council3;

	@Column(name = "d_name")
	private String name;

	@Column(name = "d_summary")
	private String summary;

	@Column(name = "d_file")
	private String file;

	@Column(name = "d_source")
	private String source;

	@ManyToOne
	@JoinColumn(name = "d_category_id")
	private category d_category;

	@ManyToOne
	@JoinColumn(name = "d_teacher_id")
	private teacher d_teacher;

	@OneToOne(mappedBy = "dd_document")
	private documentDetail d_documentdetail;

	@ManyToOne
	@JoinColumn(name = "d_student_id")
	private student d_student;

	@OneToMany(mappedBy = "f_document")
	private List<feedback> d_feedback;

	public document() {
	}

	public document(int id, String council1, String council2, String council3, String name, String summary, String file,
			String source, category d_category, teacher d_teacher, documentDetail d_documentdetail, student d_student,
			List<feedback> d_feedback) {
		this.id = id;
		this.council1 = council1;
		this.council2 = council2;
		this.council3 = council3;
		this.name = name;
		this.summary = summary;
		this.file = file;
		this.source = source;
		this.d_category = d_category;
		this.d_teacher = d_teacher;
		this.d_documentdetail = d_documentdetail;
		this.d_student = d_student;
		this.d_feedback = d_feedback;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCouncil1() {
		return council1;
	}

	public void setCouncil1(String council1) {
		this.council1 = council1;
	}

	public String getCouncil2() {
		return council2;
	}

	public void setCouncil2(String council2) {
		this.council2 = council2;
	}

	public String getCouncil3() {
		return council3;
	}

	public void setCouncil3(String council3) {
		this.council3 = council3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public category getD_category() {
		return d_category;
	}

	public void setD_category(category d_category) {
		this.d_category = d_category;
	}

	public teacher getD_teacher() {
		return d_teacher;
	}

	public void setD_teacher(teacher d_teacher) {
		this.d_teacher = d_teacher;
	}

	public documentDetail getD_documentdetail() {
		return d_documentdetail;
	}

	public void setD_documentdetail(documentDetail d_documentdetail) {
		this.d_documentdetail = d_documentdetail;
	}

	public student getD_student() {
		return d_student;
	}

	public void setD_student(student d_student) {
		this.d_student = d_student;
	}

	public List<feedback> getD_feedback() {
		return d_feedback;
	}

	public void setD_feedback(List<feedback> d_feedback) {
		this.d_feedback = d_feedback;
	}

}
