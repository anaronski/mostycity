package pirate.mostycity.pages;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.registration.LoginPage;
import pirate.mostycity.util.Constants;

public class BasePage extends WebPage implements Constants{
	
	private static final long serialVersionUID = 1L;


	private Logger log = LoggerFactory.getLogger(BasePage.class); 
	
	
	@SpringBean
	private AccountService accountService;
	
	public BasePage(PageParameters params) {
		super(params);
		if(getCurrentAccount()==null){
			try {
				CurrentSession.get().setCurrentAccount(accountService.read(ACCOUNT_GUEST_ID));
			} catch (ServiceException e) {
				log.error(e.getMessage());
			}
		}
		
	}
	
	public Account getCurrentAccount(){
		return CurrentSession.get().getCurrentAccount();
	}
	
	public boolean isAuth(){
		return CurrentSession.get().isAuthenticated();
	}
	
	protected boolean checkAuth(){
		if(!isAuth()){
			setResponsePage(LoginPage.class);
			return false;
		}else{
			return true;
		}
	}
	
	protected void  putPage(Page page) {
		CurrentSession.get().setPrevPage(page);
	}
	
	protected Page  getPrevPage() {
		return CurrentSession.get().getPrevPage();
	}
	
	public boolean isActive(){
		return isAuth() && getCurrentAccount().getAccountStatus().getId().equals(ACCOUNT_STATUS_ACTIVE);
	}
	
	public boolean isSuperAdmin(){
		return (isAuth() && getCurrentAccount().getAccountType().getId()>=ACCOUNT_TYPE_SUPERADMIN) && isActive();
	} 
	
	public boolean isAdmin(){
		return (isAuth() && getCurrentAccount().getAccountType().getId()>=ACCOUNT_TYPE_ADMIN) && isActive(); 
	}
	
	public boolean isModerator(){
		return (isAuth() && getCurrentAccount().getAccountType().getId()>=ACCOUNT_TYPE_MODERATOR) && isActive(); 
	}
	
	public boolean isAdminMode(){
		return isAdmin() && CurrentSession.get().isAdminMode();
	}
	public void setAdminMode(boolean adminMode){
		CurrentSession.get().setAdminMode(adminMode);
	}
}
