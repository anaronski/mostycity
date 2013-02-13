package pirate.mostycity.pages.mycabinet.editaccount;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;

import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.pages.mycabinet.MyCabinetMainPage;
import pirate.mostycity.pages.registration.LoginPage;
import pirate.mostycity.utils.ResourceHelper;

public class ChangePasswordPage extends MyCabinetMainPage{

	private static final long serialVersionUID = 1L;

	@SpringBean
	private AccountService accountService;
	
	private PasswordTextField oldPassTF;
	private PasswordTextField newPassTF;
	private PasswordTextField confPassTF;
	
	private String oldPass;
	private String newPass;
	private String confPass;
	
	private CustomFeedbackPanel feedback;
	
	
	public ChangePasswordPage(PageParameters parameters) {
		super(parameters);
		if(!isAdmin() && !account.getId().equals(getCurrentAccount().getId())){
			setResponsePage(LoginPage.class);
		}
	}

	@Override
	protected void initComponents() {

		account = getCurrentAccount();
		
		feedback = new CustomFeedbackPanel("feedback");
		add(feedback.setOutputMarkupId(true));
		
		Form<ValueMap> form = new Form<ValueMap>("changePasswordForm"){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onValidate() {
			
				super.onValidate();
				if(oldPassTF.getRawInput()==null || !oldPassTF.getRawInput().equals(account.getUserAuth().getPassword()))
					error(ResourceHelper.getString("changePassword.mess.error.oldInvalid", this));
				if (!newPassTF.getRawInput().equals(confPassTF.getRawInput())){
					error(ResourceHelper.getString("changePassword.mess.error.areDifferenf", this));
				}
			}
		};
		form.setOutputMarkupId(true);
		add(form);
		
		form.add(oldPassTF = new PasswordTextField("oldPassTF", new PropertyModel<String>(this, "oldPass")));
		form.add(newPassTF = new PasswordTextField("newPassTF", new PropertyModel<String>(this, "newPass")));
		form.add(confPassTF = new PasswordTextField("confPassTF", new PropertyModel<String>(this, "confPass")));
		
		form.add(new AjaxLink<Void>("cancelBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AccountInfoPage.class);
			}
		});
		
		form.add(new AjaxSubmitLink("updateBtn", form){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(account!=null){
					account.getUserAuth().setPassword(newPass);
					accountService.updateAccount(account, account.getId());
					feedback.info(ResourceHelper.getString("changePassword.mess.info.success", this));
				}else{
					setResponsePage(LoginPage.class);
				}
				
				target.add(form);
				target.add(feedback);
			}
			
		});
	}

	public String getOldPass() {
		return oldPass;
	}


	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}


	public String getNewPass() {
		return newPass;
	}


	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}


	public String getConfPass() {
		return confPass;
	}


	public void setConfPass(String confPass) {
		this.confPass = confPass;
	}
	

}
