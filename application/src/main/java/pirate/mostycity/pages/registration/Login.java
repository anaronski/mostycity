package pirate.mostycity.pages.registration;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.UserAuth;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.UserAuthService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.home.HomePage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ResourceHelper;

public class Login extends WebMarkupContainer implements Constants{
	
	private Logger log = LoggerFactory.getLogger(Login.class); 
	
	@SpringBean
	private AccountService accountService;
	
	@SpringBean
	private UserAuthService userAuthService;
	
	private String login, password;
	
	private static final long serialVersionUID = 1L;
	
	
	public Login(String id) {
		super(id);
		Form<Void> form = new Form<Void>("loginForm"){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onValidate() {
				super.onValidate();
				if(hasError()){
					setResponsePage(new LoginPage());
				}
			}
		};
		add(form);
		form.add(new RequiredTextField<Void>("login", new PropertyModel<Void>(this, "login")));
		form.add(new PasswordTextField("password", new PropertyModel<String>(this, "password")));
		form.add(new SubmitLink("loginBtn", form){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				UserAuth user = userAuthService.getByLogin(login);
				if(user!=null){
					if(user.getPassword().equals(password)){
						Account currentAccount = null;
						try {
							currentAccount = accountService.read(user.getId());
						} catch (ServiceException e) {
							log.error(e.getMessage());
						}
						if(currentAccount.getAccountStatus().getId().equals(ACCOUNT_STATUS_ACTIVE)){
							CurrentSession.get().setCurrentAccount(currentAccount);
							setResponsePage(HomePage.class, new PageParameters());
							log.info(String.format("Account %s (%s) is logined.", currentAccount.getUserAuth().getLogin(), currentAccount.getId()));
						}else{
							if(currentAccount.getAccountStatus().getId().equals(ACCOUNT_STATUS_INACTIVE))
								onWrongLogin(ResourceHelper.getString("login.message.error.userInactive", this));
							else
								onWrongLogin(ResourceHelper.getString("login.message.error.invaligLogin", this));
						}
					}else{
						onWrongLogin(ResourceHelper.getString("login.message.error.invaligLogin", this));
					}
				}else{
					onWrongLogin(ResourceHelper.getString("login.message.error.invaligLogin", this));
				}
			}
		});
		form.add(new Link<Void>("forgetBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ForgetPasswordPage.class);
			}
		});
	}

	protected void onWrongLogin(String message){
		
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
