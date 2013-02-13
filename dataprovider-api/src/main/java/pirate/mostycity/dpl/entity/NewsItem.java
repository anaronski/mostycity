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
@Table(name="news_item") 
public class NewsItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;	
	private Account accountId;
	private Date createTs;
	private int viewedCount;
	private NewsItemStatus newsItemStatus;
	private Long lastModAccountId;
	private boolean isMainFlag;
	private String newsItemTitle;
	private String newsItemDesc;
	private String imagePath;
	
	
	@Id
	@GeneratedValue
	@Column(name="news_item_id")
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
	public void setAccountId(Account accountId) {
		this.accountId = accountId;
	}
	
	@Column(name="create_ts")
	public Date getCreateTs() {
		return createTs;
	}
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
	
	@Column(name="viewed_count")
	public int getViewedCount() {
		return viewedCount;
	}
	public void setViewedCount(int viewedCount) {
		this.viewedCount = viewedCount;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "news_item_status_id")
	public NewsItemStatus getNewsItemStatus() {
		return newsItemStatus;
	}
	public void setNewsItemStatus(NewsItemStatus newsItemStatus) {
		this.newsItemStatus = newsItemStatus;
	}
	
	@Column(name="last_mod_account_id")
	public Long getLastModAccountId() {
		return lastModAccountId;
	}
	public void setLastModAccountId(Long lastModAccountId) {
		this.lastModAccountId = lastModAccountId;
	}
	
	@Column(name="is_main_flag")
	public boolean getIsMainFlag() {
		return isMainFlag;
	}
	public void setIsMainFlag(boolean isMainFlag) {
		this.isMainFlag = isMainFlag;
	}
	
	@Column(name="news_item_title")
	public String getNewsItemTitle() {
		return newsItemTitle;
	}
	public void setNewsItemTitle(String newsItemTitle) {
		this.newsItemTitle = newsItemTitle;
	}
	
	@Column(name="news_item_desc")
	public String getNewsItemDesc() {
		return newsItemDesc;
	}
	public void setNewsItemDesc(String newsItemDesc) {
		this.newsItemDesc = newsItemDesc;
	}
	
	@Column(name="image_path")
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	

}
