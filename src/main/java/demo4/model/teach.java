package demo4.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "teach")
public class teach implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private pk_teach pk_teach;

	@Embeddable
	public static class pk_teach implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "te_teacher_id")
		private String teacher_id;

		@Column(name = "te_classroom_id")
		private String classroom_id;

		public pk_teach() {
		}

		public pk_teach(String teacher_id, String classroom_id) {
			this.teacher_id = teacher_id;
			this.classroom_id = classroom_id;
		}

		public String getTeacher_id() {
			return teacher_id;
		}

		public void setTeacher_id(String teacher_id) {
			this.teacher_id = teacher_id;
		}

		public String getClassroom_id() {
			return classroom_id;
		}

		public void setClassroom_id(String classroom_id) {
			this.classroom_id = classroom_id;
		}

	}

	@Column(name = "te_semester")
	private String semester;

	@Column(name = "te_year")
	private String year;

	@ManyToOne
	@JoinColumn(name = "te_teacher_id", insertable = false, updatable = false)
	private teacher te_teacher;

	@ManyToOne
	@JoinColumn(name = "te_classroom_id", insertable = false, updatable = false)
	private classroom te_classroom;

	public teach() {

	}

	public teach(demo4.model.teach.pk_teach pk_teach, String semester, String year, teacher te_teacher,
			classroom te_classroom) {
		this.pk_teach = pk_teach;
		this.semester = semester;
		this.year = year;
		this.te_teacher = te_teacher;
		this.te_classroom = te_classroom;
	}

	public pk_teach getPk_teach() {
		return pk_teach;
	}

	public void setPk_teach(pk_teach pk_teach) {
		this.pk_teach = pk_teach;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public teacher getTe_teacher() {
		return te_teacher;
	}

	public void setTe_teacher(teacher te_teacher) {
		this.te_teacher = te_teacher;
	}

	public classroom getTe_classroom() {
		return te_classroom;
	}

	public void setTe_classroom(classroom te_classroom) {
		this.te_classroom = te_classroom;
	}

}
