package pirate.mostycity.pages.mycabinet.editaccount;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.pages.home.HomePage;
import pirate.mostycity.pages.mycabinet.MyCabinetMainPage;
import pirate.mostycity.pages.registration.LoginPage;

public class ConfirmDeletePage extends MyCabinetMainPage{

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private AccountService accountService;
	
	
	public ConfirmDeletePage(PageParameters parameters) {
		super(parameters);
		
	}
	
	@Override
	protected void initComponents() {

		add(new Link<Void>("deleteAccBtn"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				if(account!=null){
					CurrentSession.get().setCurrentAccount(null);
					accountService.deleteAccount(account, account.getId());
					setResponsePage(HomePage.class);
				}else{
					setResponsePage(LoginPage.class);
				}
			}
			
		});
		
		add(new Link<Void>("cancelBtn"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(CurrentSession.get().getPrevPage());
			}
			
		});
	}

}
