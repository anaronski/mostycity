package pirate.mostycity.pages.registration;

import pirate.mostycity.components.RegistrationPanel;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.pages.main.MainPage;
import pirate.mostycity.util.Constants;

public class RegistrationPage extends MainPage implements Constants{
	
	private static final long serialVersionUID = 1L;

	public RegistrationPage() {
		super();
		add(new RegistrationPanel("regPanel", new Account(), IS_NOT_UPDATE));
	}

}
