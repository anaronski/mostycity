package pirate.mostycity.tests;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.dpl.service.VotingService;
import pirate.mostycity.exception.ServiceException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class VotingServiceTest extends BaseServiseTest<Voting, Long>{

	@Autowired
	private VotingService votingService;

	@Override
	public void createNewEntity() {
		entity = new Voting();
		entity.setAccountId(1l);
		entity.setActiveFlag(true);
		entity.setAnswersCount(1l);
		entity.setCreateTs(new Date());
		entity.setId(1l);
		entity.setVariantsCount(2);
		entity.setVotingName("new");
	}

	@Override
	protected void modifiedEntity() {
		entity.setVotingName("new update");
	}

	@Override
	protected IBaseService<Voting, Long> getService() {
		
		return votingService;
	}
	
	@Test
	public void crud() throws ServiceException{
		super.crudTest(1l);
	}
}
