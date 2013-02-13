package pirate.mostycity.pages.mycabinet;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.main.MainPageLP;
import pirate.mostycity.pages.messages.MessagesListPage;
import pirate.mostycity.pages.mycabinet.editaccount.ChangeAvatarPage;
import pirate.mostycity.pages.mycabinet.editaccount.ChangePasswordPage;
import pirate.mostycity.pages.mycabinet.editaccount.ConfirmDeletePage;
import pirate.mostycity.pages.mycabinet.editaccount.UpdateAccountPage;
import pirate.mostycity.utils.ImageHelper;

public class MyCabinetMainPage extends MainPageLP{

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(MyCabinetMainPage.class); 
	
	@SpringBean
	private AccountService accountService;
	
	protected Account account;
	
	protected PageParameters params;
	
	protected ImageHelper imageHelper;
	
	protected StringValue accountId;
	
	public MyCabinetMainPage(PageParameters parameters) {
		super();
		checkAuth();
		accountId = parameters.get(ACCOUNT_ID);
		if(!accountId.isEmpty()){
			try {
				account = accountService.read(accountId.toLongObject());
			} catch (ServiceException e) {
				log.error(e.getMessage());
			}
		}else{
			account = getCurrentAccount();
		}
		
		params = new PageParameters();
		
		imageHelper = new ImageHelper();
		
		init();
	}
	
	private void init(){
		add(new Link<Void>("accInfoLnk"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				if(!accountId.isEmpty())
					params.add(ACCOUNT_ID, accountId);
				setResponsePage(AccountInfoPage.class, params);
			}
			
		});
		
		add(new Link<Void>("updateAccLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(UpdateAccountPage.class, params);
			}
			
			@Override
			public boolean isVisible() {
				
				return account.getId().equals(getCurrentAccount().getId());
			}
		});
		
		add(new Link<Void>("changePasswordLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ChangePasswordPage.class, params);
			}
			
			@Override
			public boolean isVisible() {
				
				return account.getId().equals(getCurrentAccount().getId());
			}
		});
		
		add(new Link<Void>("deleteAccLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ConfirmDeletePage.class, params);
			}
			
			@Override
			public boolean isVisible() {
				
				return account.getId().equals(getCurrentAccount().getId());
			}
		});
		
		add(new Link<Void>("changeAvatarLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(ChangeAvatarPage.class, params);
			}
			
			@Override
			public boolean isVisible() {
				
				return account.getId().equals(getCurrentAccount().getId());
			}
		});
		
		add(new Link<Void>("newsByAccountLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				if(!accountId.isEmpty())
					params.add(ACCOUNT_ID, accountId);
				setResponsePage(NewsByAccountPage.class, params);
			}
			
		});
		
		add(new Link<Void>("messagesLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				params.remove(INTERLOCUTER_ID);
				setResponsePage(MessagesListPage.class, params);
			}
			
			@Override
			public boolean isVisible() {
				
				return account.getId().equals(getCurrentAccount().getId());
			}
			
		});
		
//		add(new Link<Void>("newMessagesLnk"){
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void onClick() {
//				params.put(MESSAGE_TYPE, MESSAGE_TYPE_NEW);
//				setResponsePage(MessagesListPage.class, params);
//			}
//			
//			@Override
//			public boolean isVisible() {
//				
//				return account.getId().equals(getCurrentAccount().getId());
//			}
//			
//		});
//		
//		add(new Link<Void>("incommingMessagesLnk"){
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void onClick() {
//				params.put(MESSAGE_TYPE, MESSAGE_TYPE_INCOMMING);
//				setResponsePage(MessagesListPage.class, params);
//			}
//			
//			@Override
//			public boolean isVisible() {
//				
//				return account.getId().equals(getCurrentAccount().getId());
//			}
//			
//		});
//		
//		add(new Link<Void>("sentMessagesLnk"){
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void onClick() {
//				params.put(MESSAGE_TYPE, MESSAGE_TYPE_SENT);
//				setResponsePage(MessagesListPage.class, params);
//			}
//			
//			@Override
//			public boolean isVisible() {
//				
//				return account.getId().equals(getCurrentAccount().getId());
//			}
//			
//		});
		initComponents();
	}
	
	protected void initComponents(){}
}
