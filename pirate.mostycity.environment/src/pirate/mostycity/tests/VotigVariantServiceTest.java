package pirate.mostycity.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.entity.VotingVariant;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.dpl.service.VotingVariantService;
import pirate.mostycity.exception.ServiceException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class VotigVariantServiceTest extends BaseServiseTest<VotingVariant, Long>{

	@Autowired
	private VotingVariantService service;
	
	
	@Override
	public void createNewEntity() {
		entity = new VotingVariant();	
		entity.setAnswersCount(2l);
		entity.setId(15l);
		entity.setVariantName("variantName");
		entity.setVotingId(1l);
	}

	@Override
	protected void modifiedEntity() {
				
	}

	@Override
	protected IBaseService<VotingVariant, Long> getService() {
		
		return service;
	}

	@Test
	public void crud() throws ServiceException{
		super.crudTest(15l);
	}
}
