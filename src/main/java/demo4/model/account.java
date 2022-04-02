package demo4.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class account implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name = "a_id")
	private int id;

	@Column(name = "a_username")
	private String username;

	@Column(name = "a_password")
	private String password;

	@OneToMany(mappedBy = "account")
	private List<account_role> account_role;
	
	@OneToMany(mappedBy = "f_account")
	private List<feedback> a_feedback;
	

	public account() {
	}

	

	public account(int id, String username, String password, List<demo4.model.account_role> account_role,
			List<feedback> a_feedback) {

		this.id = id;
		this.username = username;
		this.password = password;
		this.account_role = account_role;
		this.a_feedback = a_feedback;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<account_role> getAccount_role() {
		return account_role;
	}

	public void setAccount_role(List<account_role> account_role) {
		this.account_role = account_role;
	}
	
	public Set<role> getRole() {
		Set<role> roles = new HashSet<role>();
		for (int i = 0; i < this.getAccount_role().size(); i++) {
			roles.add(this.getAccount_role().get(i).getRole());
		}
		return roles;
	}



	public List<feedback> getA_feedback() {
		return a_feedback;
	}



	public void setA_feedback(List<feedback> a_feedback) {
		this.a_feedback = a_feedback;
	}

}
