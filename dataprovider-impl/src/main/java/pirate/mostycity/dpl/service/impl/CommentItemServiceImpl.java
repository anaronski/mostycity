package pirate.mostycity.dpl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.ICommentItemDao;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.CommentItem;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.CommentItemService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.util.Constants;


@Service("commentItemService")
@Transactional (readOnly=false)
public class CommentItemServiceImpl extends BaseServiceImpl<CommentItem, Long> implements CommentItemService, Constants{

	
	@Autowired
	private ICommentItemDao commentItemDao;
	
	
	@Override
	public int getCommentsCount(NewsItem newsItem){
		
		return commentItemDao.getCommentsCount(newsItem, true);
	}
	@Override
	public List<CommentItem> getComments(NewsItem newsItem, boolean activeFlag, int firstResult, int maxResult) {
		
		return commentItemDao.getComments(newsItem, activeFlag, firstResult, maxResult, "createTs");
	}

	@Override
	protected IBaseDao<CommentItem, Long> getDao() throws ServiceException {
	
		return checkDao(commentItemDao);
	}
	@Override
	public void addNewComment(CommentItem commentItem) {
		
		commentItemDao.addNewComment(commentItem);
	}
	@Override
	public void deleteComment(CommentItem commentItem, Account account) {
		commentItemDao.deleteComment(commentItem, account);
	}
	
	
	@Override
	public List<CommentItem> getComments(NewsItem newsItem, boolean activeFlag) {
		return getComments(newsItem, activeFlag, firstResult, maxResult);
	}

}
