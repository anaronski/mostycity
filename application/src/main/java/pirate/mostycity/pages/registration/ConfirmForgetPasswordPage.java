package pirate.mostycity.pages.registration;

import org.apache.wicket.markup.html.link.Link;

import pirate.mostycity.pages.main.MainPage;

public class ConfirmForgetPasswordPage extends MainPage{

	private static final long serialVersionUID = 1L;

	public ConfirmForgetPasswordPage() {
		super();
		
		add(new Link<Void>("loginPageLnk"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);				
			}
		});
	}
}
