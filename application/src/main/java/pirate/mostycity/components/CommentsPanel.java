package pirate.mostycity.components;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.CommentItem;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.CommentItemService;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;
import pirate.mostycity.utils.StringHelper;

public class CommentsPanel extends Panel implements Constants{
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private CommentItemService commentItemService;
	
	private ImageHelper imageHelper = new ImageHelper();
	
	private String commentItemTxt;
	private Account account;
	private Form<ValueMap> form ;
	private CommentsListView listView;
	private List<CommentItem> list;
	private NewsItem newsItem;
	private boolean adminMode;
	private int commentsCount;
	


	public CommentsPanel(String id, NewsItem newsItem1, boolean adminMode1) {
		super(id);
		
		this.account = CurrentSession.get().getCurrentAccount();
		this.newsItem = newsItem1;
		this.adminMode = adminMode1;
		
		form = new Form<ValueMap>("commentsForm");
		form.setOutputMarkupId(true);
		add(form);
		
		commentsCount = commentItemService.getCommentsCount(newsItem);
		
		if(adminMode)
			list = commentItemService.getComments(newsItem, false, commentsCount-COMMENTS_MAX_RESULT,  COMMENTS_MAX_RESULT);
		else
			list = commentItemService.getComments(newsItem, true, commentsCount-COMMENTS_MAX_RESULT, COMMENTS_MAX_RESULT);
		
		listView = new CommentsListView("commentsListView", new PropertyModel<List<CommentItem>>(this, "list"));
		listView.setOutputMarkupId(true);
		form.add(listView);
		
//		CustomPagingNavigator pagingNavigator = new CustomPagingNavigator("pagingNavigator", listView){
//			
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public boolean isVisible() {
//				
//				return list.size()>5;
//			}
//		};
//		
//		form.add(pagingNavigator);
		
		setCommentsCount(commentItemService.getCommentsCount(newsItem));
		
		WebMarkupContainer showAllCommentsContainer = new WebMarkupContainer("showAllCommentsContainer"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				
				return (commentsCount > COMMENTS_MAX_RESULT && list.size() < commentsCount);
			}
		};
		
		
		AjaxLink<Void> showAllCommentsLink = new AjaxLink<Void>("showAllCommentsLink"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
			
				if(adminMode)
					list = commentItemService.getComments(newsItem, false);
				else
					list = commentItemService.getComments(newsItem, true);
				
				target.add(form);
			}
		};
	
		showAllCommentsLink.add(new Label("commentsCount", new PropertyModel<String>(this, "commentsCount")));
		
		showAllCommentsContainer.add(showAllCommentsLink);
 		
		form.add(showAllCommentsContainer);
		
		form.add(new Label("noCommentsLabel", ResourceHelper.getString("common.noComments", this)){
		
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				
				return list.size()==0;
			}
		});
		
		TextArea<String> commentItemTxtTA = new TextArea<String>("commentItemTxtTA", new PropertyModel<String>(this, "commentItemTxt"));
		commentItemTxtTA.setVisible(CurrentSession.get().isAuthenticated() && newsItem.getNewsItemStatus().getId().equals(NEWS_ITEM_STATUS_ACTIVE));
		form.add(commentItemTxtTA);
		
		form.add(new AjaxSubmitLink("addNewCommentBtn", form){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				if(commentItemTxt!=null){
					CommentItem commentItem = new CommentItem();
					commentItem.setAccountId(account);
					commentItem.setCommentTxt(commentItemTxt);
					commentItem.setNewsItemId(newsItem.getId());
					commentItem.setLastModAccId(account.getId());
					commentItemService.addNewComment(commentItem);
					
					commentItemTxt = null;
					setCommentsCount(commentsCount = commentItemService.getCommentsCount(newsItem));
					
					if(adminMode)
						list = commentItemService.getComments(newsItem, false, commentsCount-COMMENTS_MAX_RESULT,  COMMENTS_MAX_RESULT);
					else
						list = commentItemService.getComments(newsItem, true, commentsCount-COMMENTS_MAX_RESULT, COMMENTS_MAX_RESULT);
					
				}
				
				
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				
				target.add(form);
			}
			
			@Override
			public boolean isVisible() {
				
				return CurrentSession.get().isAuthenticated() && newsItem.getNewsItemStatus().getId().equals(NEWS_ITEM_STATUS_ACTIVE);
			}

		});
	}

	
	public class CommentsListView extends ListView<CommentItem> implements Constants{
		
		private static final long serialVersionUID = 1L;
		
		public CommentsListView(String id, PropertyModel<List<CommentItem>> propertyModel) {
			super(id, propertyModel);
		}

		@Override
		protected void populateItem(ListItem<CommentItem> item) {
			
			final CommentItem commentItem = item.getModelObject();
			
			final PageParameters parameters = new PageParameters();
//			parameters.put(COMMENT_ITEM_ID, commentItem.getId());
			parameters.add(ACCOUNT_ID, commentItem.getAccountId().getId());
			
			if (item.getIndex() != 0 && item.getIndex()% 5 != 0) {
				item.add(new AttributeModifier("class", "commentsInfo"));
			}else{
				item.add(new AttributeModifier("class", "commentsInfo first"));
			}
		
			if(!commentItem.isActiveFlag())
				item.add(new AttributeAppender("class", new Model<String>("deleted"), " "));
			
			item.add(new Label("createTs", ResourceHelper.getString("common.created", this, StringHelper.formatDate(commentItem.getCreateTs()))));
			
			item.add(new AjaxSubmitLink("deleteLnk"){

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
					
					commentItemService.deleteComment(commentItem, account);
					
					if(adminMode)
						list = commentItemService.getComments(newsItem, false);
					else
						list = commentItemService.getComments(newsItem, true);
//					listView.setCurrentPage(0);	
					target.add(form);
				}
				
				@Override
				public boolean isVisible() {
					
					return CurrentSession.get().isAuthenticated()&&account.getAccountType().getId()>=ACCOUNT_TYPE_MODERATOR && commentItem.isActiveFlag();
				}
			});
			
			String avatarUrl = imageHelper.getAvatarUrl("small/"+commentItem.getAccountId().getId().toString());
			
			item.add(new BookmarkablePageLink<Void>("authorLnk1",  AccountInfoPage.class, parameters)
					.add(new ContextImage("avatar", avatarUrl)));
			
			item.add(new BookmarkablePageLink<Void>("authorLnk",  AccountInfoPage.class, parameters)
					.add(new Label("author", commentItem.getAccountId().getUserAuth().getLogin())));
			
			item.add(new Label("commentItemDesc", StringHelper.toMultilineMarkup(commentItem.getCommentTxt()))
					.setEscapeModelStrings(false));
			
		}
		
	}


	public String getCommentItemTxt() {
		return commentItemTxt;
	}


	public void setCommentItemTxt(String commentItemTxt) {
		this.commentItemTxt = commentItemTxt;
	}


	public List<CommentItem> getList() {
		return list;
	}


	public void setList(List<CommentItem> list) {
		this.list = list;
	}
	
	public int getCommentsCount() {
		return commentsCount;
	}


	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}
}
