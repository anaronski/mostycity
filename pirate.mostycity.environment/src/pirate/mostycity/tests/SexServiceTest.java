package pirate.mostycity.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.entity.Sex;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.dpl.service.SexService;
import pirate.mostycity.exception.ServiceException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class SexServiceTest extends BaseServiseTest<Sex, Long>{
	
	@Autowired
	private SexService sexService;
	

	@Test
	public void crud() throws ServiceException{
		crudTest(10l);
	}
	
	@Before
	@Override
	public void createNewEntity() {
		entity = new Sex();
		entity.setId(10l);
		entity.setName("name");
		
	}

	@Override
	protected void modifiedEntity() {
		entity.setName("new name");		
	}

	@Override
	protected IBaseService<Sex, Long> getService() {
		
		return sexService;
	}

}
