package pirate.mostycity.pages.registration;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;

import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.pages.main.MainPage;

public class LoginPage extends MainPage{
	
	private static final long serialVersionUID = 1L;

	public LoginPage() {
		super();
		final CustomFeedbackPanel feedback = new CustomFeedbackPanel("feedback");
		add(feedback);
		WebMarkupContainer loginPanel = new Login("loginPanel"){

			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onWrongLogin(String message) {
				
				feedback.error(message);
			}
		};
		loginPanel.add(new Link<Void>("registrationBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(RegistrationPage.class);
			}
		});
		add(loginPanel);
	}

}
