package pirate.mostycity.pages.news;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.CommentsPanel;
import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.CommentItemService;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.main.MainPageLP;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;
import pirate.mostycity.utils.StringHelper;

public class NewsItemPage extends MainPageLP implements Constants{
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(NewsItemPage.class);
	
	@SpringBean
	private NewsItemService newsItemService;
	
	@SpringBean
	private CommentItemService commentItemService;
	
	@SpringBean
	private AccountService accountService;
	
	private Label newsItemFullDesc;
	private NewsItem newsItem;
	private WebMarkupContainer descPanel;
	
	public NewsItemPage(PageParameters parameters) {
		super();
		putPage(getPage());
		
		StringValue newsItemId = parameters.get(NEWS_ITEM_ID);
		
		if(newsItemId.isEmpty()){
			setResponsePage(NewsPage.class);
		}
		
		try {
			newsItem = newsItemService.read(newsItemId.toLongObject());
		} catch (ServiceException e) {
			log.error(e.getMessage());
			setResponsePage(NewsItemErrorPage.class);
			return;
		}
		
		if(!parameters.get("message").isEmpty())
			info(parameters.get("message"));
		
		parameters = null;
		
		if(newsItem!=null){
			newsItem.setViewedCount(newsItem.getViewedCount()+1);
			try {
				newsItemService.update(newsItem);
			} catch (ServiceException e) {
				log.error(e.getMessage());
			}
			
		}else{
			setResponsePage(NewsItemErrorPage.class);
			return;
		}
		
		Form<Void> form = new Form<Void>("newsItemForm"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return newsItem != null;
			}
		};
		
		form.setOutputMarkupId(true);
		add(form);
		
		final CustomFeedbackPanel feedback = new CustomFeedbackPanel("feedback");
		form.add(feedback);
		
		final PageParameters params = new PageParameters();
		params.add(ACCOUNT_ID, newsItem.getAccountId().getId());
		
		form.add(new Label("createTs", ResourceHelper.getString("common.created", this, StringHelper.formatDate(newsItem.getCreateTs()))));		
		form.add(new BookmarkablePageLink<Void>("authorLnk", AccountInfoPage.class, params)
				.add(new Label("author", newsItem.getAccountId().getUserAuth().getLogin())));
		String login = "";
		try {
			login = accountService.read(newsItem.getLastModAccountId()).getUserAuth().getLogin();
		} catch (ServiceException e) {
			log.error(e.getMessage());
		}
		form.add(new Link<Void>("lastUpdateAccountLnk"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				params.add(ACCOUNT_ID, newsItem.getLastModAccountId());
				setResponsePage(AccountInfoPage.class, params);
			}
			
			@Override
			public boolean isVisible() {
				return isAdmin();
			};
			
		}.add(new Label("lastModAcc", "last modified by " + login))); 
		
		form.add(new Label("viewedCount", ResourceHelper.getString("news.viewedCount", this, String.valueOf(newsItem.getViewedCount()))));
		
		Label newsItemTitle = new Label("newsItemTitle", newsItem.getNewsItemTitle());
		if(newsItem.getNewsItemStatus().getId().equals(NEWS_ITEM_STATUS_DELETED))
			newsItemTitle.add(new AttributeAppender("class", new Model<String>("deleted"), " "));
		form.add(newsItemTitle);
		
		final String newsItemDescB = newsItem.getNewsItemDesc();
		
		descPanel = new WebMarkupContainer("descPanel");
		descPanel.setOutputMarkupId(true);
		
		newsItemFullDesc = new Label("newsItemFullDesc", StringHelper.toMultilineMarkup(new String(newsItemDescB)));
		newsItemFullDesc.setEscapeModelStrings(false);
		newsItemFullDesc.setOutputMarkupId(true);
		descPanel.add(newsItemFullDesc);
		
		String imageName = newsItem.getImagePath();
		final String newsItemImagePath = ImageHelper.getMessageImageUrl(imageName);
		ContextImage newsItemImage = new ContextImage("newsItemImage", new Model<String>(newsItemImagePath)){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return newsItemImagePath != null && newsItemImagePath.length()>0;
			}
		};
		
		ExternalLink linkToImage = new ExternalLink("linkToImage", "../" + newsItemImagePath);
		linkToImage.add(new AttributeModifier("rel", "prettyPhoto[g_"+imageName+"]"));
		linkToImage.add(new AttributeModifier("title", ""));
		linkToImage.add(newsItemImage);
		
		descPanel.add(linkToImage);
		
		form.add(descPanel);
		
		form.add(new Link<Void>("edit"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				PageParameters param = new PageParameters();
				param.add(NEWS_ITEM_ID, newsItem.getId());
				setResponsePage(NewsItemUpdatePage.class, param);			
			}
			
			@Override
			public boolean isVisible() {
				
				return isModerator()||(getCurrentAccount()!=null &&
						getCurrentAccount().getId().equals(newsItem.getAccountId().getId()));
			}
		});
		
		form.add(new Link<Void>("delete"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				newsItem.setLastModAccountId(getCurrentAccount().getId());
				newsItemService.deleteNewsItem(newsItem);
				setResponsePage(NewsPage.class);
			}
			
			@Override
			public boolean isVisible() {
				
				return isModerator()&& !newsItem.getNewsItemStatus().getId().equals(NEWS_ITEM_STATUS_DELETED);
			}
		});
		
		form.add(new Link<Void>("onHome"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				newsItem.setIsMainFlag(true);
				newsItem.setLastModAccountId(getCurrentAccount().getId());
				try {
					newsItemService.update(newsItem);
				} catch (ServiceException e) {
					log.error(e.getMessage());
				}
				feedback.info(ResourceHelper.getString("news.message.onHome", this));
			}
			
			@Override
			public boolean isVisible() {
				
				return isModerator()&&!newsItem.getIsMainFlag()&&newsItem.getNewsItemStatus().getId().equals(NEWS_ITEM_STATUS_ACTIVE);
			}
		});
		
		form.add(new Link<Void>("fromHome"){

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				newsItem.setIsMainFlag(false);
				newsItem.setLastModAccountId(getCurrentAccount().getId());
				try {
					newsItemService.update(newsItem);
				} catch (ServiceException e) {
					log.error(e.getMessage());
				}
				feedback.info(ResourceHelper.getString("news.message.fromHome", this));
			}
			
			@Override
			public boolean isVisible() {
				
				return isModerator() && newsItem.getIsMainFlag();
			}
		});
		CommentsPanel commentsPanel = new CommentsPanel("commentsPanel", newsItem, isAdminMode());
		add(commentsPanel);
	}
}
