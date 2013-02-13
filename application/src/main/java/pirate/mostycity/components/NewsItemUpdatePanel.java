package pirate.mostycity.components;

import java.io.IOException;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.CurrentSession;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.NewsItem;
import pirate.mostycity.dpl.service.NewsItemService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.news.NewsItemPage;
import pirate.mostycity.pages.news.NewsPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;
import pirate.mostycity.utils.StringHelper;

public class NewsItemUpdatePanel extends Panel implements Constants{
	
	private static final long serialVersionUID = 1L;
	
	private Logger log = LoggerFactory.getLogger(NewsItemUpdatePanel.class);

	
	@SpringBean
	private NewsItemService newsItemService;
	
	private ImageHelper imageHelper = new ImageHelper();
	
	private NewsItem newsItem; 
	private String newsItemDesc, newsItemTitle, messImgPath;
	private FileUploadField fileUploadField;
	private List<FileUpload> fileUpload;
	private Form<ValueMap> form;
	private ContextImage messImg;
	
	private static final Long uploadMaxSize = 500000l;
	
	public NewsItemUpdatePanel(String id, NewsItem newsItemMod, final Account account, final boolean isUpdate) {
		super(id);
		
		this.newsItem = newsItemMod;
		if(newsItem!=null && newsItem.getNewsItemDesc()!=null)
			newsItemDesc = new String(newsItem.getNewsItemDesc());
		if(newsItem!=null && newsItem.getNewsItemTitle()!=null)
			newsItemTitle = new String(newsItem.getNewsItemTitle());
		messImgPath = ImageHelper.getMessageImageUrl(newsItem.getImagePath());
		
		final CustomFeedbackPanel feedback = new CustomFeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);
		
		form = new Form<ValueMap>("newsItemUpdateForm"){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				List<FileUpload> uploadList = fileUploadField.getFileUploads();
				if (uploadList != null && !uploadList.isEmpty() && uploadList.get(0) != null) {
					FileUpload upload = uploadList.get(0);
					if(uploadMaxSize < upload.getSize()){
						feedback.error(ResourceHelper.getString("upload.message.less", this, "500K"));
					}else{
						String extension = StringHelper.getFileExtension(upload.getClientFileName());
						if (!"jpg".equals(extension)&&!"png".equals(extension)&&!"gif".equals(extension)&&!"bmp".equals(extension)){ 
							this.error(ResourceHelper.getString("common.message.error.wrongFormat", this));
						}else{
							String imageFileName = imageHelper.getFileName("", extension);
							newsItem.setImagePath(imageFileName);
							newsItem.setNewsItemTitle(newsItemTitle);
							newsItem.setNewsItemDesc(newsItemDesc);
							try {
								imageHelper.saveNewsImage(upload, imageFileName);
							} catch (IOException e) {
								log.error(e.getMessage());
							}
							
							messImgPath = ImageHelper.getMessageImageUrl(imageFileName);
						}
					}
					
				}
			}
		};
		add(form);
		
		form.setOutputMarkupId(true);
		form.setMultiPart(true);
		
		
		form.add(new TextField<String>("newsItemTitle", new PropertyModel<String>(this, "newsItemTitle")));
		
		form.add(new TextArea<Object>("newsItemDescTA", new PropertyModel<Object>(this, "newsItemDesc")));
		
		form.add(new SubmitLink("addNewBtn", form){
		
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				if(newsItemTitle==null || newsItemTitle.equals("")
						|| newsItemDesc==null || newsItemDesc.equals("")){
					feedback.error(ResourceHelper.getString("common.message.error.fillFields", this));
				}else{
					newsItem.setAccountId(account);
					newsItem.setNewsItemTitle(newsItemTitle);
					newsItem.setNewsItemDesc(newsItemDesc);
					newsItem.setLastModAccountId(account.getId());
					
					newsItemService.addNewsItem(newsItem);
					setResponsePage(NewsPage.class);
				}
			}
			
			@Override
			public boolean isVisible() {
				
				return !isUpdate;
			}
		});
		
		form.add(new Link<Void>("cancelBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(CurrentSession.get().getPrevPage());
			}
		});
		
		form.add(new SubmitLink("updateBtn", form){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				if(newsItemTitle==null || newsItemTitle.equals("")
						|| newsItemDesc==null || newsItemDesc.equals("")){
					feedback.error(ResourceHelper.getString("common.message.error.fillFields", this));
				}else{
					newsItem.setNewsItemTitle(newsItemTitle);
					newsItem.setNewsItemDesc(newsItemDesc);
					newsItem.setLastModAccountId(account.getId());
					
					try {
						newsItemService.update(newsItem);
					} catch (ServiceException e) {
						log.error(e.getMessage(), e);
					}
					PageParameters params = new PageParameters();
					params.add(NEWS_ITEM_ID, newsItem.getId());
					params.add("message",ResourceHelper.getString("news.message.info.updateSuccess", this));
					setResponsePage(NewsItemPage.class, params);
				}
			}
			
			@Override
			public boolean isVisible() {
				
				return isUpdate;
			}
		});
		
		messImg = new ContextImage("messImage", new PropertyModel<String>(this, "messImgPath"));
		messImg.setOutputMarkupId(true);
		WebMarkupContainer messImageDiv = new WebMarkupContainer("messImageDiv"){

			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isVisible() {
				return messImgPath != null && messImgPath.length() > 0 ;
			}
		};
		messImageDiv.add(messImg);
		form.add(messImageDiv);
		
		messImageDiv.add(new SubmitLink("deleteMessImage", form){

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit() {
				newsItem.setImagePath(null);
				
				messImgPath = null;
			}
			
		});
		
		fileUploadField = new FileUploadField("fileUpload", new PropertyModel<List<FileUpload>>(this, "fileUpload"));
		form.add(fileUploadField);
		
	}


	public String getNewsItemDesc() {
		return newsItemDesc;
	}


	public void setNewsItemDesc(String newsItemDesc) {
		this.newsItemDesc = newsItemDesc;
	}


	public String getNewsItemTitle() {
		return newsItemTitle;
	}


	public void setNewsItemTitle(String newsItemTitle) {
		this.newsItemTitle = newsItemTitle;
	}


	public String getMessImgPath() {
		return messImgPath;
	}


	public void setMessImgPath(String messImgPath) {
		this.messImgPath = messImgPath;
	}

	public List<FileUpload> getFileUpload() {
		return fileUpload;
	}


	public void setFileUpload(List<FileUpload> fileUpload) {
		this.fileUpload = fileUpload;
	}
}
