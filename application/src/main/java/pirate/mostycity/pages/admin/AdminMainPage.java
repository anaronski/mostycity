package pirate.mostycity.pages.admin;

import org.apache.wicket.markup.html.link.Link;

import pirate.mostycity.pages.main.MainPage;
import pirate.mostycity.pages.registration.LoginPage;

public class AdminMainPage extends MainPage{

	private static final long serialVersionUID = 1L;

	public AdminMainPage() {
		super();
		
		if(isAdmin()){
			add(new Link<Void>("userPageLnk"){
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void onClick() {
					setResponsePage(UsersPage.class);				
				}
			});
			
			add(new Link<Void>("votingsPageLnk"){
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void onClick() {
					setResponsePage(VotingsPage.class);				
				}
			});
			
			initComponents();
		}else{
			setResponsePage(LoginPage.class);
		}
	}
	
	protected void initComponents(){}
}
