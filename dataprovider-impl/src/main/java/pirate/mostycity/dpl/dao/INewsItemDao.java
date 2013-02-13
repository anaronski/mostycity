package pirate.mostycity.dpl.dao;

import java.util.List;

import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.NewsItem;

public interface INewsItemDao extends IBaseDao<NewsItem, Long>{

	public List<NewsItem> getList(Account account, boolean isMain, boolean forAdminMode, int firstResult,
			int maxResults, String order);
	public int getNewsCount(Account account);
}
