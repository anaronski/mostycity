package pirate.mostycity.dpl.service;

import java.util.List;

import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.CommentItem;
import pirate.mostycity.dpl.entity.NewsItem;

public interface CommentItemService extends IBaseService<CommentItem, Long>{

	public int getCommentsCount(NewsItem newsItem);
	
	public List<CommentItem> getComments(NewsItem newsItem, boolean activeFlag);
	
	public void addNewComment(CommentItem commentItem);
	
	public void deleteComment(CommentItem commentItem, Account account);

	List<CommentItem> getComments(NewsItem newsItem, boolean activeFlag, int firstResult, int maxResult);
}
