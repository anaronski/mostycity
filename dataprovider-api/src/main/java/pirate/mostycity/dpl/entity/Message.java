package pirate.mostycity.dpl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="messages")
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long toAccountId;
	private Account fromAccountId;
	private Date createTs;
	private String messageTxt;
	private boolean newFlag;
	private Long deleteAccountId;
	
	
	@Id
	@GeneratedValue
	@Column(name="message_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="to_account_id")
	public Long getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity=Account.class)
	@JoinColumn(name = "from_account_id")
	public Account getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(Account fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	
	@Column(name="create_ts")
	public Date getCreateTs() {
		return createTs;
	}
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
	
	@Column(name="message_txt")
	public String getMessageTxt() {
		return messageTxt;
	}
	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}
	
	@Column(name="new_flag")
	public boolean getNewFlag() {
		return newFlag;
	}
	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}
	
	@Column(name="delete_account_id")
	public Long getDeleteAccountId() {
		return deleteAccountId;
	}
	public void setDeleteAccountId(Long deleteAccountId) {
		this.deleteAccountId = deleteAccountId;
	}
}
