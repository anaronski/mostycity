package pirate.mostycity.pages.registration;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.UserAuth;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.UserAuthService;
import pirate.mostycity.dpl.service.impl.CustomMailSender;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.main.MainPage;
import pirate.mostycity.utils.ResourceHelper;

public class ForgetPasswordPage extends MainPage{
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(ForgetPasswordPage.class); 
	
	@SpringBean
	private CustomMailSender mailSender;
	
	@SpringBean
	private AccountService accountService;
	
	@SpringBean
	private UserAuthService userAuthService;
	
	private String login;
	private String email;
	private Account account;
	private CustomFeedbackPanel feedback;
	
	
	public ForgetPasswordPage() {
		super();
		
		feedback = new CustomFeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);
		
		Form<ValueMap> form = new Form<ValueMap>("form");
		add(form);
		
		form.add(new RequiredTextField<Void>("login", new PropertyModel<Void>(this, "login")));
		form.add(new RequiredTextField<String>("email", new PropertyModel<String>(this, "email")));
		
		form.add(new SubmitLink("sendBtn", form){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				UserAuth user = userAuthService.getByLogin(login);
				if(user!=null){
					try {
						account = accountService.read(user.getId());
					} catch (ServiceException e) {
						log.error(e.getMessage());
					}
					if(email.equals(account.getAccountInfo().getEmail())){
						mailSender.sendPassword(account);
						setResponsePage(ConfirmForgetPasswordPage.class);
					}else
						feedback.error(ResourceHelper.getString("common.message.error.invalidEmail", this));
				}else
					feedback.error(ResourceHelper.getString("common.message.error.userDoesntExist", this, login));
			}
		});
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
}
