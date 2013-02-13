package pirate.mostycity.dpl.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.INewsItemDao;
import pirate.mostycity.dpl.dao.INewsItemStatusDao;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.util.Constants;

@Service("newsItemService")
@Transactional (readOnly=false)
public class NewsItemServiceImpl extends BaseServiceImpl<NewsItem, Long> implements NewsItemService, Constants{

	
	@Autowired
	private INewsItemDao newsItemDao;
	
	@Autowired
	private INewsItemStatusDao newsItemStatusDao;
	
	@Override
	public void addNewsItem(NewsItem newsItem) {
		newsItem.setCreateTs(new Date());
		newsItem.setViewedCount(0);
		newsItem.setNewsItemStatus(newsItemStatusDao.get(NEWS_ITEM_STATUS_ACTIVE));
		newsItemDao.save(newsItem);
	}

	@Override
	protected IBaseDao<NewsItem, Long> getDao() throws ServiceException {
		
		return checkDao(newsItemDao);
	}
	
	public List<NewsItem> getMainList(){
		
		return newsItemDao.getList(null, true, false, firstResult, maxResult, "createTs");
	}

	@Override
	public List<NewsItem> getList(boolean forAdminMode) {
		
		return newsItemDao.getList(null, false, forAdminMode, firstResult, maxResult, "createTs");
	}

	@Override
	public void deleteNewsItem(NewsItem newsItem) {
		newsItem.setNewsItemStatus(newsItemStatusDao.get(NEWS_ITEM_STATUS_DELETED));
		newsItem.setIsMainFlag(false);
		newsItemDao.update(newsItem);
	}

	@Override
	public List<NewsItem> getListByAccount(Account account, boolean forAdminMode) {
		
		return newsItemDao.getList(account, false, forAdminMode, firstResult, maxResult, "createTs");
	}

	@Override
	public int getNewsCountByAccount(Account account) {
		
		return newsItemDao.getNewsCount(account);
	}
	
}
