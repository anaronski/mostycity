package pirate.mostycity.pages.home;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

import pirate.mostycity.components.NewsPanel;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.pages.main.MainPageLP;


public class HomePage extends MainPageLP{

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private NewsItemService newsItemService;
	
	public HomePage() {
		super();
		putPage(getPage());
		List<NewsItem> list = newsItemService.getMainList();
		add(new NewsPanel("newsPanel", list, MAIN_NEWS_MAX_RESULT));
	}

}
