package pirate.mostycity.pages.news;

import pirate.mostycity.components.NewsItemUpdatePanel;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.pages.main.MainPageLP;
import pirate.mostycity.util.Constants;

public class NewsItemNewPage extends MainPageLP implements Constants{

	private static final long serialVersionUID = 1L;

	public NewsItemNewPage() {
		super();
		
		checkAuth();
		
		add(new NewsItemUpdatePanel("newsItemUpdatePanel", new NewsItem(), getCurrentAccount(), IS_NOT_UPDATE));
	}
}
