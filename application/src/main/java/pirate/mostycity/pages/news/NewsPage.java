package pirate.mostycity.pages.news;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.components.NewsPanel;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.pages.main.MainPageLP;

public class NewsPage extends MainPageLP{
	
	private static final long serialVersionUID = 1L;
	@SpringBean
	private NewsItemService service;
	
	
	public NewsPage() {
		super();
		putPage(getPage());
		
		List<NewsItem> list = service.getList(CurrentSession.get().isAdminMode());
		add(new NewsPanel("newsPanel", list, NEWS_MAX_RESULT));
	}

}
