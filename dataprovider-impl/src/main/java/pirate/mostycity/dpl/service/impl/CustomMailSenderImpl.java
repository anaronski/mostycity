package pirate.mostycity.dpl.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import pirate.mostycity.dpl.entity.Account;

@Service("customMailSender")
public class CustomMailSenderImpl extends JavaMailSenderImpl implements CustomMailSender{
	
	private String host;
	private int port;
	private String username;
	private String password;
	private String from;
	private String to;
	
	public CustomMailSenderImpl() {
		super();
	}
	
	

	@Override
	public void sendConfirmRegistration(Account account) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Hello, ");
		if(account.getFirstName()==null && account.getLastName()==null){
			builder.append(account.getUserAuth().getLogin());
		}else{
			builder.append(setNull(account.getFirstName()))
				.append(" ")
				.append(setNull(account.getLastName()));
		}
		builder.append("! \n")
			.append("Your account has been successfully created on site mostycity.com.")
			.append("\n\n")
			.append("login: ")
			.append(account.getUserAuth().getLogin())
			.append("\n")
			.append("password: ")
			.append(account.getUserAuth().getPassword())
			.append("\n\n")
			.append("Welcome to mostycity.com");
		
		sendFromMessage(account.getAccountInfo().getEmail(), "Create Account", builder.toString());
	}

	@Override
	public void sendPassword(Account account) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Hello, ");
		if(account.getFirstName()==null && account.getLastName()==null){
			builder.append(account.getUserAuth().getLogin());
		}else{
			builder.append(setNull(account.getFirstName()))
				.append(" ")
				.append(setNull(account.getLastName()));
		}
		builder.append("! \n")
			.append("You have requested the password to access the your account on site mostycity.com.")
			.append("\n\n")
			.append("login: ")
			.append(account.getUserAuth().getLogin())
			.append("\n")
			.append("password: ")
			.append(account.getUserAuth().getPassword());
		
		sendFromMessage(account.getAccountInfo().getEmail(), "Forget Password", builder.toString());
	}
	
	private void sendMessage(String from, String to, String subject, String text){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setText(text);
		
		send(message);
	}
	
	private String setNull(String s){
		return s==null ? "" : s;
	}
	
	private void sendToMessage(String from, String subject, String text){
		sendMessage(from, to, subject, text);
	}
	
	private void sendFromMessage(String to, String subject, String text){
		sendMessage(from, to, subject, text);
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}