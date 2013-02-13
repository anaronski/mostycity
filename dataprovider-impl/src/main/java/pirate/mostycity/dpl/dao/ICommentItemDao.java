package pirate.mostycity.dpl.dao;

import java.util.List;

import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.CommentItem;
import pirate.mostycity.dpl.entity.NewsItem;

public interface ICommentItemDao extends IBaseDao<CommentItem, Long>{
	
	public int getCommentsCount(NewsItem newsItem, boolean activeFlag);
	
	public List<CommentItem> getComments(NewsItem newsItem, boolean activeFlag, int firstResult,
			int maxResult, String order);
	
	public void addNewComment(CommentItem commentItem);
	
	public void deleteComment(CommentItem commentItem, Account account);
	
}
