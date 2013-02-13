package pirate.mostycity.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.service.CustomMailSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class MailSenderTest {
	
	@Autowired
	private CustomMailSender mailSender;
	
	
	@Test
	public void sendTest(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("pirateweter@gmail.com");
		message.setTo("pirateweter@gmail.com");
		message.setText("proba");
		
		mailSender.send(message);
	}
}
