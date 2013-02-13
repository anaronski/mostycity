package pirate.mostycity.tests;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.entity.Message;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.dpl.service.MessageService;
import pirate.mostycity.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class MessageServiceTest extends BaseServiseTest<Message, Long>{

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private AccountService accountService;
	
	
	
	@Override
	public void createNewEntity() throws ServiceException {
		entity = new Message();
		entity.setCreateTs(new Date());
		entity.setFromAccountId(accountService.read(1l));
		entity.setId(1l);
		entity.setMessageTxt("message");
		entity.setNewFlag(true);
		entity.setToAccountId(1l);
		
	}

	@Override
	protected void modifiedEntity() {
		entity.setMessageTxt("new Message");
	}

	@Override
	protected IBaseService<Message, Long> getService() {
		
		return messageService;
	}

	@Test
	public void crud() throws ServiceException{
		super.crudTest(1l);
	}
}
