package pirate.mostycity.pages.mycabinet.editaccount;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import pirate.mostycity.components.RegistrationPanel;
import pirate.mostycity.pages.mycabinet.MyCabinetMainPage;
import pirate.mostycity.pages.registration.LoginPage;
import pirate.mostycity.util.Constants;

public class UpdateAccountPage extends MyCabinetMainPage implements Constants{

	private static final long serialVersionUID = 1L;

	public UpdateAccountPage(PageParameters parameters) {
		super(parameters);
		if(!isAdmin() && !account.getId().equals(getCurrentAccount().getId())){
			setResponsePage(LoginPage.class);
		}
		add(new RegistrationPanel("regPanel", account, IS_UPDATE));
	}
}
