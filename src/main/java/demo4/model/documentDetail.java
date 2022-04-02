package demo4.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "documentdetail")
public class documentDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private pk_documentDetail pk_documentDetail;

	@Embeddable
	public static class pk_documentDetail implements java.io.Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "dd_classroom_id")
		private String classroom_id;

		@Column(name = "dd_student_id")
		private String student_id;

		public pk_documentDetail(String classroom_id, String student_id) {
			this.classroom_id = classroom_id;
			this.student_id = student_id;
		}

		public pk_documentDetail() {
		}

		public String getClassroom_id() {
			return classroom_id;
		}

		public void setClassroom_id(String classroom_id) {
			this.classroom_id = classroom_id;
		}

		public String getStudent_id() {
			return student_id;
		}

		public void setStudent_id(String student_id) {
			this.student_id = student_id;
		}

		

	}

	@Column(name = "dd_check")
	private boolean check;

	@OneToOne
	@JoinColumn(name = "dd_document_id")
	private document dd_document;

	@ManyToOne
	@JoinColumn(name = "dd_classroom_id", insertable = false, updatable = false)
	private classroom dd_classroom;

	@ManyToOne
	@JoinColumn(name = "dd_student_id", insertable = false, updatable = false)
	private student dd_student;

	public documentDetail() {
	}

	public documentDetail(demo4.model.documentDetail.pk_documentDetail pk_documentDetail, boolean check,
			document dd_document, classroom dd_classroom, student dd_student) {
		this.pk_documentDetail = pk_documentDetail;
		this.check = check;
		this.dd_document = dd_document;
		this.dd_classroom = dd_classroom;
		this.dd_student = dd_student;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public document getDd_document() {
		return dd_document;
	}

	public void setDd_document(document dd_document) {
		this.dd_document = dd_document;
	}

	public classroom getDd_classroom() {
		return dd_classroom;
	}

	public void setDd_classroom(classroom dd_classroom) {
		this.dd_classroom = dd_classroom;
	}

	public student getDd_student() {
		return dd_student;
	}

	public void setDd_student(student dd_student) {
		this.dd_student = dd_student;
	}

	public pk_documentDetail getPk_documentDetail() {
		return pk_documentDetail;
	}

	public void setPk_documentDetail(pk_documentDetail pk_documentDetail) {
		this.pk_documentDetail = pk_documentDetail;
	}

}
