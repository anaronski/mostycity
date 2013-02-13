package pirate.mostycity.dpl.service;

import java.util.List;

import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.NewsItem;

public interface NewsItemService extends IBaseService<NewsItem, Long>{

	public void addNewsItem(NewsItem newsItem);
	public List<NewsItem> getMainList();
	public List<NewsItem> getList(boolean forAdminmode);
	public void deleteNewsItem(NewsItem newsItem);
	public List<NewsItem> getListByAccount(Account account, boolean forAdmin);
	public int getNewsCountByAccount(Account account);
} 
