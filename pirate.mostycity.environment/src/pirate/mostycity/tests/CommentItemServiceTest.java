package pirate.mostycity.tests;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.dao.IAccountDao;
import pirate.mostycity.dpl.dao.INewsItemDao;
import pirate.mostycity.dpl.entity.CommentItem;
import pirate.mostycity.dpl.service.CommentItemService;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.exception.ServiceException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class CommentItemServiceTest extends BaseServiseTest<CommentItem, Long>{

	@Autowired
	private CommentItemService service;
	
	@Autowired
	private IAccountDao accountDao;
	
	@Autowired
	private INewsItemDao newsItemDao;
	
	
	@Before
	@Override
	public void createNewEntity() {
		entity = new CommentItem();
		entity.setAccountId(accountDao.get(1l));
		entity.setActiveFlag(true);
		entity.setCommentTxt("cjvvtynfhbb");
		entity.setCreateTs(new Date());
		entity.setId(1l);
		entity.setNewsItemId(1l);
	}
	
	@Test
	public void crud() throws ServiceException{
		crudTest(1l);
	}

	@Test
	public void getCommentsCountTest(){
		service.getCommentsCount(newsItemDao.get(1l));
	}
	
	@Override
	protected void modifiedEntity() {
		entity.setActiveFlag(false);
		
	}

	@Override
	protected IBaseService<CommentItem, Long> getService() {
		
		return service;
	}

}
