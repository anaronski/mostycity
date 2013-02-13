package pirate.mostycity.dpl.dao.impl;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.INewsItemStatusDao;
import pirate.mostycity.dpl.entity.NewsItemStatus;

@Repository
public class NewsItemStatusDaoImpl extends BaseDaoImpl<NewsItemStatus, Long> implements INewsItemStatusDao{

	protected NewsItemStatusDaoImpl() {
		super(NewsItemStatus.class);
	}

}
