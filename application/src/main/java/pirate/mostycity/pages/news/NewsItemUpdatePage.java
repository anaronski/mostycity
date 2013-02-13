package pirate.mostycity.pages.news;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.NewsItemUpdatePanel;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.home.HomePage;
import pirate.mostycity.pages.main.MainPageLP;
import pirate.mostycity.util.Constants;

public class NewsItemUpdatePage extends MainPageLP implements Constants{

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(NewsItemUpdatePage.class); 
	
	@SpringBean
	private NewsItemService newsItemService;
	
	
	public NewsItemUpdatePage(PageParameters parameters) {
		super();

		NewsItem newsItem = null;
		StringValue newsItemId = parameters.get(NEWS_ITEM_ID);
		if(newsItemId.isEmpty()){
			setResponsePage(NewsPage.class);
		}
		try {
			newsItem = newsItemService.read(newsItemId.toLongObject());
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}
		
		if(newsItem == null){
			setResponsePage(NewsPage.class);
		}
		
		if(isModerator()||(getCurrentAccount()!=null&&getCurrentAccount().getId().equals(newsItem.getAccountId().getId())))
			add(new NewsItemUpdatePanel("newsItemUpdatePanel", newsItem, getCurrentAccount(), IS_UPDATE));
		else
			setResponsePage(HomePage.class);
	}
}
