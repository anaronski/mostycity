package pirate.mostycity.pages.messages;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.SendMessageFormPanel;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.Message;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.MessageService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.pages.mycabinet.MyCabinetMainPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;
import pirate.mostycity.utils.StringHelper;

public class DialogPage extends MyCabinetMainPage{

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(DialogPage.class); 
	
	private ImageHelper imageHelper = new ImageHelper();
	
	@SpringBean
	private MessageService messageService;
	
	@SpringBean
	private AccountService accountService;
	
//	@SpringBean
//	private JmsSender jmsSender;
//	
//	@SpringBean
//	private TopicConnectionFactory connectionFactory;

	
	private Long interlocutorId;
	private List<Message> list;
	private Form<ValueMap> form;
	private String messageTxt;
	private MessagesListView listView;
	private Account interlocutor;
	private WebMarkupContainer listViewContainer;
//	private AbstractDefaultAjaxBehavior click;
	
//	private TopicConnection topicConnection;
//	private TopicSession session;
//	private	Topic topic;
//	private TopicSubscriber subscriber;
	
	public DialogPage(PageParameters params) {
		super(params);
		
//		try {
//			topicConnection = connectionFactory.createTopicConnection();
//			session = topicConnection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
//			topic = session.createTopic("pirate.mostycity.mainQueue"); 
//			subscriber = session.createSubscriber(topic);
//			
//			topicConnection.start();
//			subscriber.receiveNoWait();
//			
//		} catch (JMSException e2) {
//			e2.printStackTrace();
//		}
		
		final Long currentId = getCurrentAccount().getId();
		StringValue interId = params.get(INTERLOCUTER_ID);
		if(interId.isEmpty()){
			setResponsePage(MessagesListPage.class);
		}else{
			interlocutorId = interId.toLongObject();
			try {
				interlocutor = accountService.read(interlocutorId);
			} catch (ServiceException e) {
				log.error(e.getMessage());
				setResponsePage(MessagesListPage.class);
			}
			
			if(interlocutor == null){
				setResponsePage(MessagesListPage.class);
				return;
			}else{
				if(interlocutor.getAccountStatus().getId().equals(ACCOUNT_STATUS_DELETED)){
					messageTxt = "This account was deleted.";
				}
			}
			
		}
		
		
		list = messageService.getDialogMessages(currentId, interlocutorId);
		form = new Form<ValueMap>("messagesHistoryListForm"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return interlocutor != null;
			}
		};
		form.setOutputMarkupId(true);
		add(form);
		
		PageParameters parameters = new PageParameters();
		parameters.add(ACCOUNT_ID, interlocutor.getId());
		
		String avatarUrl = imageHelper.getAvatarUrl("small/" + interlocutorId);
		
		form.add(new BookmarkablePageLink<Void>("authorLnk1",  AccountInfoPage.class, parameters){

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				return !interlocutor.getAccountStatus().getId().equals(ACCOUNT_STATUS_DELETED);
			}
			
		}.add(new ContextImage("avatar", avatarUrl)));
		
//		form.add(new BookmarkablePageLink<Void>("authorLnk",  AccountInfoPage.class, parameters){
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public boolean isEnabled() {
//				return !interlocutor.getAccountStatus().getId().equals(ACCOUNT_STATUS_DELETED);
//			}
//		}.add(new Label("author", interlocutor.getUserAuth().getLogin())));
		
		listViewContainer = new WebMarkupContainer("listViewContainer");
		listViewContainer.setOutputMarkupId(true);
		listView = new MessagesListView("messagesHistoryListView", new PropertyModel<List<? extends Message>>(this, "list"));
		AjaxSelfUpdatingTimerBehavior updating = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5)){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onPostProcessTarget(AjaxRequestTarget target) {
				List<Message> newMessages = messageService.getNewDialogMessages(currentId, interlocutorId);
				if(newMessages.size() > 0){
					try {
						messageService.setReadedtoList(newMessages);
					} catch (ServiceException e) {
						log.error(e.getMessage(), e);
					}
					list.addAll(newMessages);
					getWelcomePanel().setNewMessageCount(String.valueOf(messageService.getNewMessagesCount(currentId)));
					target.add(listViewContainer);
				}
					
			}
		};
		listViewContainer.add(updating);
		listViewContainer.add(listView);
		
		form.add(listViewContainer);
		
		try {
			List<Message> newMessages = messageService.getNewDialogMessages(currentId, interlocutorId);
			messageService.setReadedtoList(newMessages);
			getWelcomePanel().setNewMessageCount(String.valueOf(messageService.getNewMessagesCount(currentId)));
		} catch (ServiceException e1) {
			log.error(e1.getMessage(), e1);
		}
		
		AbstractDefaultAjaxBehavior click = new AbstractDefaultAjaxBehavior() {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void respond(AjaxRequestTarget target) {
				 messageTxt = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("message").toString();
				 
				 if(messageTxt!=null){
						Message newMessage = new Message();
						newMessage.setFromAccountId(getCurrentAccount());
						newMessage.setMessageTxt(messageTxt);
						newMessage.setNewFlag(true);
						newMessage.setToAccountId(interlocutor.getId());
						newMessage.setCreateTs(new Date());
						newMessage.setDeleteAccountId(0l);
						
						try {
							messageService.create(newMessage);
						} catch (ServiceException e) {
							log.error(e.getMessage());
						}
						
						messageTxt = null;
						list.add(newMessage);
						
						target.add(form);
					}
			}
		};
		
		form.add(new SendMessageFormPanel("sendMessagePanel", click){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
				return !interlocutor.getAccountStatus().getId().equals(ACCOUNT_STATUS_DELETED);
			}
		});
		
		listViewContainer.add(new Label("noMessagesLabel", ResourceHelper.getString("common.noMessages", this)){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				
				return list.size()==0;
			}
		});
		
	}

	private class MessagesListView extends ListView<Message> implements Constants{

		private static final long serialVersionUID = 1L;

		public MessagesListView(String id,	IModel<? extends List<? extends Message>> model) {
			super(id, model);
		}

		@Override
		protected void populateItem(ListItem<Message> item) {
			final Message message = item.getModelObject();
			
			Long accountId = message.getFromAccountId().getId();
			
			final PageParameters parameters = new PageParameters();
			parameters.add(ACCOUNT_ID, accountId);
			
			if(message.getNewFlag() && message.getToAccountId().equals(getCurrentAccount().getId()))
				item.add(new AttributeAppender("class", new Model<String>("new"), " "));
			
			if (item.getIndex() != 0) {
				item.add(new AttributeAppender("class", new Model<String>("border"), " "));
			} 
			
			WebMarkupContainer underMessage = new WebMarkupContainer("underMessage");
			item.add(underMessage);
			
			if(message.getFromAccountId().getId().equals(getCurrentAccount().getId()))
				underMessage.add(new AttributeAppender("class", new Model<String>("sent"), " "));
			else if(message.getNewFlag())
				underMessage.add(new AttributeAppender("class", new Model<String>("new"), " "));
			else if(message.getToAccountId().equals(getCurrentAccount().getId()))
				underMessage.add(new AttributeAppender("class", new Model<String>("incomming"), " "));
				
			underMessage.add(new Label("createTs", StringHelper.formatDate(message.getCreateTs())));
			
			underMessage.add(new AjaxSubmitLink("deleteLnk"){

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
					
					messageService.deleteMessage(message, getCurrentAccount().getId());					
					list = messageService.getDialogMessages(getCurrentAccount().getId(), interlocutorId);
					
					target.add(listViewContainer);
				}
			});
			String authorName = "";
			try {
				authorName = accountService.read(accountId).getUserAuth().getLogin();
			} catch (ServiceException e) {
				log.error(e.getMessage());
			}
			item.add(new BookmarkablePageLink<Void>("authorLnk",  AccountInfoPage.class, parameters)
					.add(new Label("author", authorName)));
			
			item.add(new Label("messageDesc", StringHelper.toMultilineMarkup(message.getMessageTxt()))
					.setEscapeModelStrings(false));
		}
	}
	
	public List<Message> getList() {
		return list;
	}

	public void setList(List<Message> list) {
		this.list = list;
	}

	public String getMessageTxt() {
		return messageTxt;
	}

	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}
	
}
