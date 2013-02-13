package pirate.mostycity.pages.mycabinet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;

import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.pages.messages.DialogPage;
import pirate.mostycity.pages.mycabinet.editaccount.UpdateAccountPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ResourceHelper;

public class AccountInfoPage extends MyCabinetMainPage implements Constants{

	private static final long serialVersionUID = 1L;

	@SpringBean
	private AccountService accountService;
	
	@SpringBean
	private NewsItemService newsItemService;
	
	private CustomFeedbackPanel feedback;
	private ContextImage avatar;
	private String avatarUrl;
	private Form<ValueMap> form;
	
	public AccountInfoPage(PageParameters parameters) {
		
		super(parameters);
		
		this.params = parameters;
		putPage(getPage());
	}
	
	@Override
	protected void initComponents() {
		feedback = new CustomFeedbackPanel("feedback");
		add(feedback.setOutputMarkupId(true));
		
		form = new Form<ValueMap>("accountForm");
		add(form.setOutputMarkupId(true));
		
		avatarUrl = imageHelper.getAvatarUrl(account.getId().toString());
		
		form.add(new Link<Void>("newMessageLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters params = new PageParameters();
				params.add(INTERLOCUTER_ID, account.getId());
				setResponsePage(DialogPage.class, params);
			}
			
			@Override
			public boolean isEnabled() {
				
				return !account.getId().equals(getCurrentAccount().getId())&&
					!account.getAccountStatus().getId().equals(ACCOUNT_STATUS_DELETED);
			}
			
		}.add(new Label("login", account.getUserAuth()!=null?account.getUserAuth().getLogin():"")));
		
		form.add(new Label("firstNameLabel", ResourceHelper.getString("common.fName", this)+": ").setVisible(account.getFirstName()!=null));
		form.add(new Label("firstName", account.getFirstName()).setVisible(account.getFirstName()!=null));
		
		form.add(new Label("lastNameLabel", ResourceHelper.getString("common.lName", this)+": ").setVisible(account.getLastName()!=null));
		form.add(new Label("lastName", account.getLastName()).setVisible(account.getLastName()!=null));
		
		form.add(new Label("accountId", ResourceHelper.getString("common.id", this, account.getId().toString())).setVisible(isAdmin()));		
		form.add(new Label("type", ResourceHelper.getString("accountInfo.type", this,account.getAccountType().getName())));		
		form.add(new Label("status", new PropertyModel<String>(account, "accountStatus.name")));
		form.add(new Label("newsCount", ResourceHelper.getString("accountInfo.newsCount", this, String.valueOf(newsItemService.getNewsCountByAccount(account)))));
		
		WebMarkupContainer accountInfoPanel = new WebMarkupContainer("accountInfoPanel");
		accountInfoPanel.setVisible(isAdmin()||!account.getAccountStatus().getId().equals(ACCOUNT_STATUS_DELETED));
		form.add(accountInfoPanel);
		
		accountInfoPanel.add(new Label("sex", account.getAccountInfo().getSex().getName()));
		accountInfoPanel.add(new Label("email", account.getAccountInfo().getEmail()));
		
		accountInfoPanel.add(new Label("aimLabel", ResourceHelper.getString("common.aim", this)+": ").setVisible(account.getAccountInfo().getAim()!=null));
		accountInfoPanel.add(new Label("aim", account.getAccountInfo().getAim()).setVisible(account.getAccountInfo().getAim()!=null));
		
		accountInfoPanel.add(new Label("isqLabel", ResourceHelper.getString("common.isq", this)+": ").setVisible(account.getAccountInfo().getIsq()!=null));
		accountInfoPanel.add(new Label("isq", account.getAccountInfo().getIsq()).setVisible(account.getAccountInfo().getIsq()!=null));
		
		accountInfoPanel.add(new Label("skypeLabel", ResourceHelper.getString("common.skype", this)+": ").setVisible(account.getAccountInfo().getSkype()!=null));
		accountInfoPanel.add(new Label("skype", account.getAccountInfo().getSkype()).setVisible(account.getAccountInfo().getSkype()!=null));
		
		accountInfoPanel.add(new Label("vkontakteLabel", ResourceHelper.getString("common.vkontakte", this)+": ").setVisible(account.getAccountInfo().getVkontakte()!=null));
		accountInfoPanel.add(new Label("vkontakte", account.getAccountInfo().getVkontakte()).setVisible(account.getAccountInfo().getVkontakte()!=null));
		
		
		WebMarkupContainer userAuthPanel = new WebMarkupContainer("userAuthPanel");
		userAuthPanel.setVisible(isSuperAdmin() && account.getUserAuth()!=null);
		accountInfoPanel.add(userAuthPanel);
		
		userAuthPanel.add(new Label("password", account.getUserAuth()!=null?account.getUserAuth().getPassword():""));
		
		WebMarkupContainer editButtons = new WebMarkupContainer("editButtons");
		editButtons.setVisible((isAdmin()&&account.getAccountType().getId()<ACCOUNT_TYPE_ADMIN||isSuperAdmin())
				&&!account.getId().equals(getCurrentAccount().getId())
				&&!account.getAccountStatus().getId().equals(ACCOUNT_STATUS_DELETED));
		form.add(editButtons);
		
		editButtons.add(new AjaxLink<Void>("deactivateBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				account = accountService.deactivateAccount(account, getCurrentAccount().getId());
				feedback.info(ResourceHelper.getString("accountInfo.message.info.successDeactivate", this));
				
				target.add(feedback);
				target.add(form);
			}

			@Override
			public boolean isVisible() {
				return ACCOUNT_STATUS_ACTIVE.equals(account.getAccountStatus().getId());
			}
		});
		
		editButtons.add(new AjaxLink<Void>("activateBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				account = accountService.activateAccount(account, getCurrentAccount().getId());
				feedback.info(ResourceHelper.getString("accountInfo.message.info.successActivate", this));
				
				target.add(feedback);
				target.add(form);
			}
			
			@Override
			public boolean isVisible() {
				return !ACCOUNT_STATUS_ACTIVE.equals(account.getAccountStatus().getId());
			}

		});
		
		editButtons.add(new Link<Void>("editBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters parameters = new PageParameters();
				parameters.add(ACCOUNT_ID, account.getId());
				setResponsePage(UpdateAccountPage.class, parameters);
			}
			
		});
		
		editButtons.add(new AjaxLink<Void>("deleteBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				account = accountService.deleteAccount(account, getCurrentAccount().getId());
				feedback.info(ResourceHelper.getString("accountInfo.message.info.successDelete", this));
				
				target.add(feedback);
				target.add(form);				
			}
			
		});
		
		
		avatar = new ContextImage("avatar", new PropertyModel<String>(this, "avatarUrl"));
		avatar.setOutputMarkupId(true);
		form.add(avatar);
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}

