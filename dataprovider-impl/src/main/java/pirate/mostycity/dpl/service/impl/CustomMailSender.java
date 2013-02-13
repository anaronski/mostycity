package pirate.mostycity.dpl.service.impl;

import org.springframework.mail.javamail.JavaMailSender;

import pirate.mostycity.dpl.entity.Account;

public interface CustomMailSender extends JavaMailSender{

	public void sendPassword(Account account);
	
	public void sendConfirmRegistration(Account account);
}
