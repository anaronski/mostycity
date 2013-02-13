package pirate.mostycity.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.entity.UserAuth;
import pirate.mostycity.dpl.service.UserAuthService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class UserAuthServiceTest{

	@Autowired
	private UserAuthService service;
	
	private String login = "pirate";
	
	@Test
	public void getByLoginTest(){
		UserAuth user = service.getByLogin(login);
	}

}
