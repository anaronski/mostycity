package pirate.mostycity.pages.main;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.pages.home.HomePage;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.pages.news.NewsItemNewPage;
import pirate.mostycity.pages.news.NewsPage;
import pirate.mostycity.pages.registration.LoginPage;
import pirate.mostycity.pages.registration.RegistrationPage;

public class HeaderMenu extends Panel{

	private static final long serialVersionUID = 1L;

	public HeaderMenu(String id) {
		super(id);
		
		add(new BookmarkablePageLink<Void>("addNewsLnk", NewsItemNewPage.class));
		add(new BookmarkablePageLink<Void>("boardLnk", NewsPage.class));
		add(new BookmarkablePageLink<Void>("loginLnk", LoginPage.class)
				.setVisible(!CurrentSession.get().isAuthenticated()));
		
		WebMarkupContainer registerLi = new WebMarkupContainer("registerLi");
		registerLi.setVisible(!CurrentSession.get().isAuthenticated());
		add(registerLi.add(new BookmarkablePageLink<Void>("registerLnk", RegistrationPage.class)));
		
		WebMarkupContainer myCabinetLi = new WebMarkupContainer("myCabinetLi");
		myCabinetLi.setVisible(CurrentSession.get().isAuthenticated());
		add(myCabinetLi.add(new BookmarkablePageLink<Void>("myCabinetLnk", AccountInfoPage.class, new PageParameters())));
		
		add(new Link<Void>("logoutLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				CurrentSession.get().setCurrentAccount(null);
				CurrentSession.get().setAdminMode(false);
				setResponsePage(HomePage.class);
			}
			
			@Override
			public boolean isVisible() {
				
				return CurrentSession.get().isAuthenticated();
			}
			
		});
		
	}

}
