package pirate.mostycity.components;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

public class CustomPagingNavigator extends PagingNavigator{

	private static final long serialVersionUID = 1L;
	
	private int viewSize = 50;
	private int margin = 10;
	private String separator = "|";
	

	public CustomPagingNavigator(String id, IPageable pageable) {
		super(id, pageable);
	}
	
	@Override
	protected PagingNavigation newNavigation(String id, IPageable pageable,
			IPagingLabelProvider labelProvider) {
		
		PagingNavigation pageNavigation =  super.newNavigation(id, pageable, labelProvider);
		pageNavigation.setSeparator(separator);
		pageNavigation.setViewSize(viewSize);
		pageNavigation.setMargin(margin);
		
		return pageNavigation;
	}
	
}
