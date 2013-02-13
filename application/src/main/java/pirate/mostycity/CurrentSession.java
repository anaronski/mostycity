package pirate.mostycity;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.util.Constants;

public class CurrentSession extends WebSession implements Constants{

	private static final long serialVersionUID = 1L;
	
	private Account currentAccount;
	private Page prevPage;
	private boolean adminMode;

//	protected transient HttpSession httpSession;

	public CurrentSession(Request request) {
		super(request);

//		httpSession = ((WebRequest) request).getHttpServletRequest()
//				.getSession();
//		setLocale(new Locale("ru"));
		adminMode = false;
		
	}

	public static CurrentSession get() {

		return (CurrentSession) Session.get();
	}

	public void setCurrentAccount(Account account) {
		this.currentAccount = account;
	}
	
	public Account getCurrentAccount(){
		return currentAccount;
	}

	public boolean isAuthenticated() {
		return getCurrentAccount()!=null && !ACCOUNT_GUEST_ID.equals(getCurrentAccount().getId());
	}

	public Page getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(Page currentPage) {
		this.prevPage = currentPage;
	}

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}
	
}
