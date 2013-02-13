package pirate.mostycity.dpl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="votings")
public class Voting implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String votingName;
	private Long accountId;
	private Date createTs;
	private Long answersCount;
	private int variantsCount;
	private boolean activeFlag;
	
	
	@Id
	@GeneratedValue
	@Column(name="voting_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="account_id")
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Column(name="create_ts")
	public Date getCreateTs() {
		return createTs;
	}
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
	
	@Column(name="answers_count")
	public Long getAnswersCount() {
		return answersCount;
	}
	public void setAnswersCount(Long answersCount) {
		this.answersCount = answersCount;
	}
	
	@Column(name="variants_count")
	public int getVariantsCount() {
		return variantsCount;
	}
	public void setVariantsCount(int variantsCount) {
		this.variantsCount = variantsCount;
	}
	
	@Column(name="active_flag")
	public boolean getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	@Column(name="voting_name")
	public String getVotingName() {
		return votingName;
	}
	public void setVotingName(String votingName) {
		this.votingName = votingName;
	}
	
}
