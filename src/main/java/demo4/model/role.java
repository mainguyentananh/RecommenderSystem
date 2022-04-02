package demo4.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class role implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "r_id")
	private int id;

	@Column(name = "r_name")
	private String name;

	@OneToMany(mappedBy = "role")
	private List<account_role> account_role;

	public role() {
	}

	public role(int id, String name, List<demo4.model.account_role> account_role) {
		this.id = id;
		this.name = name;
		this.account_role = account_role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<account_role> getAccount_role() {
		return account_role;
	}

	public void setAccount_role(List<account_role> account_role) {
		this.account_role = account_role;
	}

}
