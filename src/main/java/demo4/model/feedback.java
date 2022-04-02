package demo4.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "feedback")
public class feedback implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "f_id")
	private int id;

	@Column(name = "f_score")
	private int score;

	@Column(name = "f_comment")
	private String comment;

	@Column(name = "f_time")
	private Date time;

	@ManyToOne
	@JoinColumn(name = "f_account_id")
	private account f_account;

	@ManyToOne
	@JoinColumn(name = "f_document_id")
	private document f_document;

	public feedback() {
	}

	

	public feedback(int id, int score, String comment, Date time, account f_account, document f_document) {
		this.id = id;
		this.score = score;
		this.comment = comment;
		this.time = time;
		this.f_account = f_account;
		this.f_document = f_document;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}



	public document getF_document() {
		return f_document;
	}

	public void setF_document(document f_document) {
		this.f_document = f_document;
	}



	public account getF_account() {
		return f_account;
	}



	public void setF_account(account f_account) {
		this.f_account = f_account;
	}
	

}
