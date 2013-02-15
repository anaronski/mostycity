package pirate.mostycity.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.service.MessageService;
import pirate.mostycity.pages.messages.MessagesListPage;
import pirate.mostycity.pages.mycabinet.editaccount.ChangeAvatarPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;

public class WelcomePanel extends Panel implements Constants{

	private static final long serialVersionUID = 1L;

	@SpringBean
	private MessageService messageService;
	
	private ImageHelper imageHelper = new ImageHelper();
	
	private String newMessageCount = "0";
	private Account currentUser;
	private Label newMessCount;
	private String avatarUrl = "";
	
	public WelcomePanel(String id) {
		super(id);
		currentUser = CurrentSession.get().getCurrentAccount();
		avatarUrl = "";
		if(currentUser!=null)
			avatarUrl = imageHelper.getAvatarUrl("small/"+currentUser.getId().toString());
		
		add(new BookmarkablePageLink<Void>("changeAvatarLnk",  ChangeAvatarPage.class, new PageParameters())
				.add(new ContextImage("avatar1", new PropertyModel<String>(this, "avatarUrl"))));
		
		String userName = "";
		if(currentUser!=null){
			userName = (currentUser.getFirstName()!=null)?currentUser.getFirstName():currentUser.getUserAuth().getLogin();
			newMessageCount = String.valueOf(messageService.getNewMessagesCount(currentUser.getId()));
		}
		add(new Label("userName", ResourceHelper.getString("welcome.welcome", this, userName)));
		
		newMessCount = new Label("newMessCount", new PropertyModel<String>(this, "newMessageCount"));
		newMessCount.setOutputMarkupId(true);
		
		AjaxSelfUpdatingTimerBehavior updating = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onPostProcessTarget(AjaxRequestTarget target) {
				newMessageCount = String.valueOf(messageService.getNewMessagesCount(currentUser.getId()));
				target.add(newMessCount);
			}
		};
		
		// TODO
		
//		add(updating);
		
		add(new Link<Void>("messagesLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters params = new PageParameters();
				setResponsePage(MessagesListPage.class, params);				
			}
			
			@Override
			public boolean isEnabled() {
				return !newMessageCount.equals("0");
			}
			
		}.add(newMessCount));
		
		
	}


	public String getAvatarUrl() {
		return avatarUrl;
	}


	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}


	public String getNewMessageCount() {
		return newMessageCount;
	}


	public void setNewMessageCount(String newMessageCount) {
		this.newMessageCount = newMessageCount;
	}

}
