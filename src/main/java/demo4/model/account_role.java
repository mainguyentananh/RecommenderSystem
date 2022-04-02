package demo4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account_role")
public class account_role implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private account account;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private role role;

	public account_role() {
	}

	public account_role(int id, demo4.model.account account, demo4.model.role role) {
		this.id = id;
		this.account = account;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public account getAccount() {
		return account;
	}

	public void setAccount(account account) {
		this.account = account;
	}

	public role getRole() {
		return role;
	}

	public void setRole(role role) {
		this.role = role;
	}

}
