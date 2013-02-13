package pirate.mostycity.dpl.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.ICommentItemDao;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.CommentItem;
import pirate.mostycity.dpl.entity.NewsItem;


@Repository
public class CommentItemDaoImpl extends BaseDaoImpl<CommentItem, Long> implements ICommentItemDao{

	public CommentItemDaoImpl() {
		super(CommentItem.class);
	}

	@Override
	public int getCommentsCount(NewsItem newsItem, boolean activeFlag) {
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("newsItemId", newsItem.getId());
		if(activeFlag)
			properties.put("activeFlag", activeFlag);
		
		return getCountByEqualProperty(null, properties);
	}

	@Override
	public List<CommentItem> getComments(NewsItem newsItem, boolean activeFlag, int firstResult,
			int maxResult, String order){
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("newsItemId", newsItem.getId());
		if(activeFlag)
			properties.put("activeFlag", true);
		
		return getByEqualProperty(null, properties, firstResult, maxResult, order, false);
	}

	@Override
	public void addNewComment(CommentItem commentItem) {
		
		commentItem.setActiveFlag(true);
		commentItem.setCreateTs(new Date());
		
		save(commentItem);
	}

	@Override
	public void deleteComment(CommentItem commentItem, Account account) {
		
		commentItem.setLastModAccId(account.getId());
		commentItem.setActiveFlag(false);
		
		update(commentItem);
	}

}
