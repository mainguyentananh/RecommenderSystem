package demo4.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tempcosinesimilarity")
public class tempcosinesimilarity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "temp_key")
	private String key;

	@Column(name = "temp_time")
	private Date time;
	
	@Column(name="temp_vectorname")
	private String vectorName;
	
	@Column(name= "temp_cosinesimilarity")
	private String cosineSimilarity;

	@Column(name = "temp_count")
	private int countVectorName;
	
	public tempcosinesimilarity() {
	}

	


	public tempcosinesimilarity(String key, Date time, String vectorName, String cosineSimilarity, int countVectorName) {
		this.key = key;
		this.time = time;
		this.vectorName = vectorName;
		this.cosineSimilarity = cosineSimilarity;
		this.countVectorName = countVectorName;
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




	public int getCountVectorName() {
		return countVectorName;
	}




	public void setCountVectorName(int countVectorName) {
		this.countVectorName = countVectorName;
	}
	
	
	
}
