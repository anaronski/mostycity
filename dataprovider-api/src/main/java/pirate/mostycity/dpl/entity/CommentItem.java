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
@Table(name="comment_item")
public class CommentItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Account accountId;
	private Long newsItemId;
	private Date createTs;
	private String commentTxt;
	private boolean activeFlag;
	private Long lastModAccId;
	
	
	@Id
	@GeneratedValue
	@Column(name="comment_item_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	public Account getAccountId() {
		return accountId;
	}
	public void setAccountId(Account acountId) {
		this.accountId = acountId;
	}
	
	@Column(name="news_item_id")
	public Long getNewsItemId() {
		return newsItemId;
	}
	public void setNewsItemId(Long newsItemId) {
		this.newsItemId = newsItemId;
	}
	
	@Column(name="create_ts")
	public Date getCreateTs() {
		return createTs;
	}
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
	
	@Column(name="comment_txt")
	public String getCommentTxt() {
		return commentTxt;
	}
	public void setCommentTxt(String commentTxt) {
		this.commentTxt = commentTxt;
	}
	
	@Column(name="active_flag")
	public boolean isActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	@Column(name="last_mod_account_id")
	public Long getLastModAccId() {
		return lastModAccId;
	}
	public void setLastModAccId(Long lastModAccId) {
		this.lastModAccId = lastModAccId;
	}
}
