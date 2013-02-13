package pirate.mostycity.pages.mycabinet;

import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.components.NewsPanel;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.NewsItemService;

public class NewsByAccountPage extends MyCabinetMainPage{

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private NewsItemService newsItemService;
	
	
	public NewsByAccountPage(PageParameters parameters) {
		super(parameters);
		putPage(getPage());
	}

	@Override
	protected void initComponents() {

		List<NewsItem> list = newsItemService.getListByAccount(account, CurrentSession.get().isAdminMode());
		add(new NewsPanel("newsByAccount", list, 5));
	}
}
