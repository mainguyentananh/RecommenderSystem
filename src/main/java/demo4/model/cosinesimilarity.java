package demo4.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cosinesimilarity")
public class cosinesimilarity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cs_key")
	private String key;

	@Column(name = "cs_time")
	private Date time;
	
	@Column(name="cs_vectorname")
	private String vectorName;
	
	@Column(name= "cs_cosinesimilarity")
	private String cosineSimilarity;

	public cosinesimilarity() {
	}


	public cosinesimilarity(String key, Date time, String vectorName, String cosineSimilarity) {
		this.key = key;
		this.time = time;
		this.vectorName = vectorName;
		this.cosineSimilarity = cosineSimilarity;
	}


	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getVectorName() {
		return vectorName;
	}

	public void setVectorName(String vectorName) {
		this.vectorName = vectorName;
	}



	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}





	public String getCosineSimilarity() {
		return cosineSimilarity;
	}





	public void setCosineSimilarity(String cosineSimilarity) {
		this.cosineSimilarity = cosineSimilarity;
	}
	
	
	
}
