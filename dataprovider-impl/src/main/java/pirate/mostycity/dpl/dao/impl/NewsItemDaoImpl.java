package pirate.mostycity.dpl.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.INewsItemDao;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.NewsItem;

@Repository
public class NewsItemDaoImpl extends BaseDaoImpl<NewsItem, Long> implements INewsItemDao{

	protected NewsItemDaoImpl() {
		super(NewsItem.class);
	}

	@Override
	public List<NewsItem> getList(Account account, boolean isMain, boolean forAdminMode, int firstResult, int maxResults, String order) {
		
		List<NewsItem> list = new ArrayList<NewsItem>();
		Map<String, Object> properties = new HashMap<String, Object>();
		Criteria crit = getSession().createCriteria(getEntityClass());
		if(!forAdminMode)
			crit.add(Restrictions.ne("newsItemStatus.id", 2l));
		if(account!=null)
			properties.put("accountId.id", account.getId());
		if(isMain)
			properties.put("isMainFlag", isMain);
		
		list = getByEqualProperty(crit, properties, firstResult, maxResults, order);
		return list;
	}

	@Override
	public int getNewsCount(Account account) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("accountId.id", account.getId());
		return getCountByEqualProperty(null, properties);
	}

}
