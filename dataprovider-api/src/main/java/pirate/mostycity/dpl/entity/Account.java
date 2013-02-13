package pirate.mostycity.dpl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;


@Entity
@Table(name="account")
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;	
	private String firstName;
	private String lastName;
	private AccountType accountType;
	private AccountStatus accountStatus;
	private Date lastLoginTs;
	private Date createTs;
	private AccountInfo accountInfo;
	private Long lastUpdateAccId;
	private UserAuth userAuth;
	
	@Id
	@GeneratedValue
	@Column(name="account_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_type_id")
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_status_id")
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	@Column(name="last_login_ts")
	public Date getLastLoginTs() {
		return lastLoginTs;
	}
	public void setLastLoginTs(Date lastLoginTs) {
		this.lastLoginTs = lastLoginTs;
	}
	
	@Column(name="create_ts")
	public Date getCreateTs() {
		return createTs;
	}
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity=AccountInfo.class, cascade=CascadeType.REMOVE)
	@JoinColumn(name = "account_id")
	@Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DELETE})
	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}
	
	@Column(name="last_update_acc_id")
	public Long getLastUpdateAccId() {
		return lastUpdateAccId;
	}
	public void setLastUpdateAccId(Long lastUpdateAccId) {
		this.lastUpdateAccId = lastUpdateAccId;
	}
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity=UserAuth.class, cascade=CascadeType.REMOVE)
	@JoinColumn(name = "account_id")
	@Cascade({org.hibernate.annotations.CascadeType.DELETE})
	public UserAuth getUserAuth() {
		return userAuth;
	}
	public void setUserAuth(UserAuth userAuth) {
		this.userAuth = userAuth;
	}
	
}
