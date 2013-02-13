package pirate.mostycity.pages.main;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.pages.BasePage;
import pirate.mostycity.pages.home.HomePage;

public class Header extends Panel{
	
	private static final long serialVersionUID = 1L;
	
	public Header(String id) {
		super(id);
		
		add(new Link<Void>("homeLink"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(HomePage.class);
			}
		});
		
		add(new Link<Void>("adminMode"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				if(CurrentSession.get().isAdminMode()){
					CurrentSession.get().setAdminMode(false);
					add(new AttributeModifier("class", "adminModeOff"));
				}
				else{
					CurrentSession.get().setAdminMode(true);
					add(new AttributeModifier("class", "adminModeOn"));
				}
			}
			
			@Override
			public boolean isVisible() {
				return ((BasePage)getPage()).isAdmin();
			}
			
		}.add(new AttributeModifier("class", CurrentSession.get().isAdminMode()?"adminModeOn":"adminModeOff")));
	}

}
