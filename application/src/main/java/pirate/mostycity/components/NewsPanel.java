package pirate.mostycity.components;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.CommentItemService;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.pages.news.NewsItemPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;
import pirate.mostycity.utils.StringHelper;

public class NewsPanel extends Panel implements Constants{

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private CommentItemService commentItemService;
	
	

	public NewsPanel(String id, List<NewsItem> list, int maxResult) {
		super(id);
		Form<Void> form = new Form<Void>("newsForm");
		
		NewsListView listView = new NewsListView("newsListView", list, maxResult);
		form.add(listView);
		
		CustomPagingNavigator pagingNavigator = new CustomPagingNavigator("pagingNavigator", listView);
		
		form.add(pagingNavigator.setVisible(list.size()>maxResult));
		
		add(form);
	}
	
	private class NewsListView extends PageableListView<NewsItem> implements Constants{
		
		private String commentsCount;
		
		private static final long serialVersionUID = 1L;
		
		public NewsListView(String id, List<? extends NewsItem> list, int maxResult) {
			super(id, list, maxResult);
		}

		@Override
		protected void populateItem(ListItem<NewsItem> item) {
			
			final NewsItem newsItem = item.getModelObject();
			
			final PageParameters parameters = new PageParameters();
			parameters.add(ACCOUNT_ID, newsItem.getAccountId().getId());
			
			commentsCount = String.valueOf(commentItemService.getCommentsCount(newsItem));
			
			if (item.getIndex()!=0 && item.getIndex()% 5 != 0) {
				item.add(new AttributeAppender("class", new Model<String>("newsItem"), " "));
			} 
			
			if(newsItem.getNewsItemStatus().getId().equals(NEWS_ITEM_STATUS_DELETED)){
				
				item.add(new AttributeAppender("class", new Model<String>("deleted"), " "));
			}
			
			item.add(new Label("createTs",ResourceHelper.getString("common.created", this, StringHelper.formatDate(newsItem.getCreateTs()))));
			
			item.add(new BookmarkablePageLink<Void>("authorLnk", AccountInfoPage.class, parameters)
				.add(new Label("author", newsItem.getAccountId().getUserAuth().getLogin())));

			PageParameters params = new PageParameters();
			params.add(NEWS_ITEM_ID, newsItem.getId());
			
			item.add(new BookmarkablePageLink<Void>("newsItemLink", NewsItemPage.class, params)
				.add(new Label("newsItemTitle", newsItem.getNewsItemTitle())));
			
			item.add(new Label("newsItemDesc", StringHelper.toMultilineMarkup(StringHelper.getSubString(newsItem.getNewsItemDesc(), NEWS_ITEM_DESC_MAX_LENGTH)))
					.setEscapeModelStrings(false));
			
			Label newsItemImage = new Label("newsItemImage", ImageHelper.getHtmlForImage(newsItem.getImagePath())){
				
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible() {
					return newsItem.getImagePath() != null && newsItem.getImagePath().length()>0;
				}
			};
			newsItemImage.setEscapeModelStrings(false);
			newsItemImage.setOutputMarkupId(true);
			item.add(newsItemImage);
			
			item.add(new BookmarkablePageLink<Void>("commentsLink", NewsItemPage.class, params)
				.add(new Label("commentsCount", ResourceHelper.getString("common.comments", this, commentsCount))));
			
			
			item.add(new Label("viewedCount", ResourceHelper.getString("news.viewedCount", this, String.valueOf(newsItem.getViewedCount()))));
		}

		@SuppressWarnings("unused")
		public String getCommentsCount() {
			return this.commentsCount;
		}

		@SuppressWarnings("unused")
		public void setCommentsCount(String commentsCount) {
			this.commentsCount = commentsCount;
		}
	}

	
}