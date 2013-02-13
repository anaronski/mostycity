package pirate.mostycity.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.UserAuthService;
import pirate.mostycity.dpl.service.impl.CustomMailSender;
import pirate.mostycity.pages.registration.ConfirmRegistrationPage;
import pirate.mostycity.utils.ResourceHelper;

public class RegistrationPanel extends Panel{
	
	@SpringBean
	private AccountService accountService;
	
	@SpringBean
	private UserAuthService userAuthService;
	
	@SpringBean
	private CustomMailSender mailSender;
	
	private Account account;	
	private String emailConfirm;
	private String passwordConfirm;
	private RadioGroup<Integer> sex; 
	Form<ValueMap> form;
	
	private static final long serialVersionUID = 1L;

	public RegistrationPanel(String id, Account modAccount, final boolean isUpdate) {
		super(id);
		
		this.account = modAccount;
		
		final CustomFeedbackPanel feedback = new CustomFeedbackPanel("feedback");
		add(feedback.setOutputMarkupId(true));
		
		form = new Form<ValueMap>("registrationForm");
		add(form.setOutputMarkupId(true));
		
		CompoundPropertyModel<Account> model = new CompoundPropertyModel<Account>(account);
		form.setDefaultModel(model);
		@SuppressWarnings("unused")
		IModel<String> stringModel = null;
		@SuppressWarnings("unused")
		IModel<Integer> intModel = null;
		
		
		final RequiredTextField<String> emailField = new RequiredTextField<String>("email", stringModel = model.bind("accountInfo.email"));
		emailField.add(EmailAddressValidator.getInstance());
		form.add(emailField);
		
		final PasswordTextField passwordField = new PasswordTextField("password", stringModel = model.bind("userAuth.password"));
		WebMarkupContainer loginCont = new WebMarkupContainer("loginCont");
		loginCont.setVisible(!isUpdate);
		loginCont.add(passwordField);
		PasswordTextField passwordConfirmField = new PasswordTextField("passwordConfirm", new PropertyModel<String>(this, "passwordConfirm")){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void validate(){
				if (!passwordField.getRawInput().equals(getRawInput())){
					error("Password and Confirm Password are different");
				}
			}
		};
		loginCont.add(passwordConfirmField);
		form.add(loginCont);
		form.add(new RequiredTextField<String>("login", stringModel = model.bind("userAuth.login")));
		form.add(new TextField<String>("firstName",stringModel = model.bind("firstName")));
		form.add(new TextField<String>("lastName", stringModel = model.bind("lastName")));
		form.add(new SubmitLink("registerBtn", form){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				
				if(userAuthService.getByLogin(account.getUserAuth().getLogin())==null){
					accountService.createAccount(account);
					setResponsePage(ConfirmRegistrationPage.class);
					mailSender.sendConfirmRegistration(account);
				}else{
					feedback.error(ResourceHelper.getString("registration.message.error.exist", this, account.getUserAuth().getLogin()));
				}
			}
			
			@Override
			public boolean isVisible() {
				
				return !isUpdate;
			}
		});
		form.add(new AjaxLink<Void>("cancelBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CurrentSession.get().getPrevPage());				
			}
		});
		form.add(new AjaxSubmitLink("updateBtn", form){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				accountService.updateAccount(account, CurrentSession.get().getCurrentAccount().getId());
				feedback.info(ResourceHelper.getString("common.successfullyUpdated", this, "The account"));
				
				target.add(form);
				target.add(feedback);
			}
			
			@Override
			public boolean isVisible() {
				return isUpdate;
			}
		});
		
		sex = new RadioGroup<Integer>("sexGroup",
				intModel = model.bind("accountInfo.sex.id"));
		sex.add(new Radio<Integer>("1", new Model<Integer>(1)));
		sex.add(new Radio<Integer>("2", new Model<Integer>(2)));
		form.add(sex);
		form.add(new TextField<String>("skype", stringModel = model.bind("accountInfo.skype")));
		form.add(new TextField<String>("isq", stringModel = model.bind("accountInfo.isq")));
		form.add(new TextField<String>("aim", stringModel = model.bind("accountInfo.aim")));
		form.add(new TextField<String>("vkontakte", stringModel = model.bind("accountInfo.vkontakte")));
	}


	public String getEmailConfirm() {
		return emailConfirm;
	}

	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	

}
