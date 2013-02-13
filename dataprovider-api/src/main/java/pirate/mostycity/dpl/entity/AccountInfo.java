package pirate.mostycity.dpl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "account_info")
public class AccountInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date birthdayTs;
	private Sex sex;
	private String email;
	private String skype;
	private String isq;
	private String aim;
	private String vkontakte;
	private byte[] avatar;

	@Id
	@Column(name = "account_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "birthday_ts")
	public Date getBirthdayTs() {
		return birthdayTs;
	}

	public void setBirthdayTs(Date birthdayTs) {
		this.birthdayTs = birthdayTs;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sex_id")
	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "skype")
	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	@Column(name = "isq")
	public String getIsq() {
		return isq;
	}

	public void setIsq(String isq) {
		this.isq = isq;
	}

	@Column(name = "aim")
	public String getAim() {
		return aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	@Column(name = "vkontakte")
	public String getVkontakte() {
		return vkontakte;
	}

	public void setVkontakte(String vkontakte) {
		this.vkontakte = vkontakte;
	}

	@Column(name = "avatar")
	@Basic(fetch = FetchType.EAGER)
	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	
}