package pirate.mostycity.pages.admin;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;

import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.StringHelper;

public class UsersPage extends AdminMainPage{
	
	private static final long serialVersionUID = 1L;

	@SpringBean
	private AccountService accountService;
	
	private List<Account> usersList;
	private Form<ValueMap> form;
	private CustomFeedbackPanel feedback;
	
	private Account user;

	public UsersPage() {
		
		super();
	}

	@Override
	protected void initComponents() {
		
		usersList = accountService.getAllAccounts();
		feedback = new CustomFeedbackPanel("feedback");
		add(feedback.setOutputMarkupId(true));
		
		form = new Form<ValueMap>("usersForm");
		form.setOutputMarkupId(true);
		add(form);
		
		RadioGroup<Account> accountRadioGroup = new RadioGroup<Account>(
				"accountRadioGroup", new PropertyModel<Account>(this, "user"));
		accountRadioGroup.add(new UsersListView("usersListView", new PropertyModel<List<Account>>(this, "usersList"), 10));
		form.add(accountRadioGroup);
		
		form.add(new AjaxSubmitLink("deactivate", form){

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				
				if(user!=null){
					user = accountService.deactivateAccount(user, getCurrentAccount().getId());
					
					usersList = accountService.getAllAccounts();
					target.add(form);
					target.add(feedback);
				}
			}
			
			@Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                // update feedback to display errors
                target.add(feedback);
            }
		});
		
		form.add(new AjaxSubmitLink("activate", form){

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				
				if(user!=null){
					user = accountService.activateAccount(user, getCurrentAccount().getId());
					
					usersList = accountService.getAllAccounts();
					target.add(form);
					target.add(feedback);
				}
			}
			
			@Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                // update feedback to display errors
                target.add(feedback);
            }
		});
		
		form.add(new AjaxSubmitLink("delete", form){
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				
				if(user!=null){
					user = accountService.deleteAccount(user, getCurrentAccount().getId());
					
					usersList = accountService.getAllAccounts();
					target.add(form);
					target.add(feedback);
				}
			}
			
			@Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
                // update feedback to display errors
                target.add(feedback);
            }
		});		
	}
	private class UsersListView extends PageableListView<Account> implements Constants{

		private static final long serialVersionUID = 1L;

		public UsersListView(String id, IModel<? extends List<? extends Account>> model, int rowsPerPage) {
			super(id, model, rowsPerPage);
		}

		@Override
		protected void populateItem(ListItem<Account> item) {
			Account user = item.getModelObject();
			
			PageParameters params = new PageParameters();
			params.add(ACCOUNT_ID, user.getId());
			
			item.add(new Radio<Account>("userChoice", new Model<Account>(user)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected boolean getStatelessHint() {

					return true;
				}
			});
			
			if (item.getIndex()% 2 != 0) {
				item.add(new AttributeAppender("class", new Model<String>("gray-light"), " "));
			} 
			
			String login = "Guest";
			if(user.getUserAuth() != null){
				login = user.getUserAuth().getLogin();
			}else {
				if(user.getId() != 1){
					login = "Undefined";
				}
			}
			item.add(new BookmarkablePageLink<Void>("loginLnk",  AccountInfoPage.class, params)
					.add(new Label("login", login )));
			
			item.add(new Label("createTs", StringHelper.formatDate(user.getCreateTs())));
			
			item.add(new Label("lastLoginTs", StringHelper.formatDate(user.getLastLoginTs())));
			
			item.add(new Label("status", user.getAccountStatus().getName()));
			
			item.add(new Label("type", user.getAccountType().getName()));	
			
			item.add(new Label("sex", user.getAccountInfo().getSex().getName()));
			
			item.add(new Label("email", user.getAccountInfo().getEmail()));	
		}
		
	}

	public List<Account> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<Account> usersList) {
		this.usersList = usersList;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}
	
}
