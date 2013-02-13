package pirate.mostycity.pages.messages;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.CustomPagingNavigator;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.Message;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.MessageService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.mycabinet.MyCabinetMainPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;
import pirate.mostycity.utils.StringHelper;

public class MessagesListPage extends MyCabinetMainPage implements Constants{
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(MessagesListPage.class); 
	
	private ImageHelper imageHelper = new ImageHelper();

	@SpringBean
	private MessageService messageService; 
	
	@SpringBean
	private AccountService accountService;
	
	private List<Message> list;
	private Form<ValueMap> form;
	
	public MessagesListPage(PageParameters parameters) {
		super(parameters);
		
		updateMessagesList();
		form = new Form<ValueMap>("messagesListForm");
		form.setOutputMarkupId(true);
		add(form);
		
		MessagesListView listView = new MessagesListView("messagesListView", new PropertyModel<List<? extends Message>>(this, "list"),
				MESSAGES_ROWS_PER_PAGE);
		form.add(listView);
		
		CustomPagingNavigator pagingNavigator = new CustomPagingNavigator("pagingNavigator", listView){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				
				return list.size()>10;
			}
		};
		form.add(pagingNavigator);
		
		form.add(new Label("noMessagesLabel", ResourceHelper.getString("common.noMessages", this)){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				
				return list.size()==0;
			}
		});
	}

	private class MessagesListView extends PageableListView<Message> implements Constants{

		private static final long serialVersionUID = 1L;

		public MessagesListView(String id,	IModel<? extends List<? extends Message>> model, int rowsPerPage) {
			super(id, model, rowsPerPage);
		}

		@Override
		protected void populateItem(ListItem<Message> item) {
			final Message message = item.getModelObject();
			final Long currentId = getCurrentAccount().getId();
			Long interlocutorId = message.getFromAccountId().getId().equals(currentId)?
									message.getToAccountId():
									message.getFromAccountId().getId();
			
			final PageParameters parameters = new PageParameters();
			parameters.add(INTERLOCUTER_ID, interlocutorId);
			
			if(message.getNewFlag()&&message.getToAccountId().equals(currentId))
				item.add(new AttributeAppender("class", new Model<String>("new"), " "));
			
			if (item.getIndex()!=0 && item.getIndex()% 10 != 0) {
				item.add(new AttributeAppender("class", new Model<String>("border"), " "));
			} 
			
			Link<Void> linkToDialog = new Link<Void>("linkToDialog"){

				private static final long serialVersionUID = 1L;
				
				@Override
				public void onClick() {
					setResponsePage(DialogPage.class, parameters);
				};
				
			};
			
			
			WebMarkupContainer underMessage = new WebMarkupContainer("underMessage");
			item.add(underMessage);
			
			if(message.getFromAccountId().getId().equals(getCurrentAccount().getId()))
				underMessage.add(new AttributeAppender("class", new Model<String>("sent"), " "));
			else if(message.getNewFlag())
				underMessage.add(new AttributeAppender("class", new Model<String>("new"), " "));
			else if(message.getToAccountId().equals(getCurrentAccount().getId()))
				underMessage.add(new AttributeAppender("class", new Model<String>("incomming"), " "));
				
			underMessage.add(new Label("createTs", StringHelper.formatDate(message.getCreateTs())));
			
			item.add(new AjaxSubmitLink("deleteLnk"){

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
					
					messageService.deleteMessages(message.getToAccountId(), message.getFromAccountId().getId(), getCurrentAccount().getId());					
					updateMessagesList();
					
					target.add(form);
				}
			});
			
			String avatarUrl = imageHelper.getAvatarUrl("small/"+interlocutorId.toString());
			
			item.add(linkToDialog);
			item.add(new ContextImage("avatar", avatarUrl));
			
			Account account1 = null;
			try {
				account1 = accountService.read(interlocutorId);
			} catch (ServiceException e1) {
				log.error(e1.getMessage());
			}
			
			item.add(new Label("author", account1.getUserAuth().getLogin()));
			
			item.add(new Label("messageDesc", StringHelper.toMultilineMarkup(message.getMessageTxt().replaceAll("src=\"../", "src=\"")))
					.setEscapeModelStrings(false));
		}
		
	}
	
	private void updateMessagesList(){
		list = messageService.getDialogs(getCurrentAccount().getId());
	}

	public List<Message> getList() {
		return list;
	}

	public void setList(List<Message> list) {
		this.list = list;
	}
	
}
