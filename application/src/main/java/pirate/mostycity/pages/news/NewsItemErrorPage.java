package pirate.mostycity.pages.news;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import pirate.mostycity.pages.main.MainPageLP;

public class NewsItemErrorPage extends MainPageLP{

	private static final long serialVersionUID = 1L;
	
	public NewsItemErrorPage() {
		add(new BookmarkablePageLink<Void>("newsPageLink", NewsPage.class));
	}

}
