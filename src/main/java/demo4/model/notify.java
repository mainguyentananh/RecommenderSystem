package demo4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notify")
public class notify  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "n_id")
	private String id;
	
	@Column(name = "n_message")
	private String message;

	public notify() {
	}

	public notify(String id, String message) {
		this.id = id;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
