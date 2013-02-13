package pirate.mostycity.dpl.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="sex")
public class Sex implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	@Id
	@GeneratedValue
	@Column(name="sex_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
