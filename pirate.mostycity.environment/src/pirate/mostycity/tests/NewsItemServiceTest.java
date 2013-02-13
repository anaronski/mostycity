package pirate.mostycity.tests;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.dao.IAccountDao;
import pirate.mostycity.dpl.dao.INewsItemStatusDao;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.exception.ServiceException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class NewsItemServiceTest extends BaseServiseTest<NewsItem, Long>{

	@Autowired
	private NewsItemService newsItemService;
	
	@Autowired
	private INewsItemStatusDao newsItemStatusDao;
	
	@Autowired
	private IAccountDao accountDao;
	
	private Long id = 15l;
	
	@Before
	@Override
	public void createNewEntity() {
		
		entity =  new NewsItem();
		entity.setId(id);
		entity.setCreateTs(new Date());
		entity.setViewedCount(0);
		entity.setNewsItemStatus(newsItemStatusDao.get(1l));
		entity.setAccountId(accountDao.get(1l));
		entity.setLastModAccountId(1l);
		entity.setIsMainFlag(true);
		entity.setNewsItemTitle("title6");
		entity.setNewsItemDesc("description6");
	}

	@Override
	protected void modifiedEntity() {
		entity.setIsMainFlag(false);		
	}
	
	@Test
	public void crud() throws ServiceException{
		super.crudTest(id);
	}
	
//	@Test
	public void getMainListTest(){
		@SuppressWarnings("unused")
		List<NewsItem> list = newsItemService.getMainList();
	}

	@Override
	protected IBaseService<NewsItem, Long> getService() {
		return this.newsItemService;	
	}
}
