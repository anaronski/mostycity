package pirate.mostycity.pages.mycabinet.editaccount;

import java.io.IOException;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.pages.mycabinet.MyCabinetMainPage;
import pirate.mostycity.pages.registration.LoginPage;
import pirate.mostycity.utils.ImageHelper;
import pirate.mostycity.utils.ResourceHelper;
import pirate.mostycity.utils.StringHelper;

public class ChangeAvatarPage extends MyCabinetMainPage {
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(ChangeAvatarPage.class);
	
	private ImageHelper imageHelper = new ImageHelper();

	private FileUploadField fileUploadField;
	private List<FileUpload> fileUpload;
	private String avatarUrl;
	private ContextImage avatar;
	private Form<ValueMap> form;
	private CustomFeedbackPanel feedback;
	
	private static final Long uploadMaxSize = 500000l;

	public ChangeAvatarPage(PageParameters parameters) {
		super(parameters);
		
		if(!isAdmin() && !account.getId().equals(getCurrentAccount().getId())){
			setResponsePage(LoginPage.class);
		}
		
		avatarUrl = imageHelper.getAvatarUrl(account.getId().toString());
			
		feedback = new CustomFeedbackPanel("feedback");
		add(feedback.setOutputMarkupId(true));
		
		form = new Form<ValueMap>("changeAvatarForm");
		form.setOutputMarkupId(true);
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(500));
		add(form);
		
		fileUploadField = new FileUploadField("fileUpload",
				new PropertyModel<List<FileUpload>>(this, "fileUpload"));
		form.add(fileUploadField.setRequired(true));
		
		
		avatar = new ContextImage("avatar", new PropertyModel<String>(this, "avatarUrl"));
		avatar.setOutputMarkupId(true);
		form.add(avatar);
		
		form.add(new SubmitLink("uploadAvatarBtn"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
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
							String fileName = account.getId().toString()+".jpg";
							try {
								imageHelper.saveAvatar(upload, fileName);
								feedback.info(ResourceHelper.getString("changeAvatar.message.info.success", this));
							} catch (IOException e) {
								log.error(e.getMessage());
							}
							avatarUrl = imageHelper.getAvatarUrl(account.getId().toString());
						}
					}
				}
				getWelcomePanel().setAvatarUrl(avatarUrl);
			}
			
		});
	}

	
	public List<FileUpload> getFileUpload() {
		return fileUpload;
	}


	public void setFileUpload(List<FileUpload> fileUpload) {
		this.fileUpload = fileUpload;
	}


	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl() {
		return this.avatarUrl;
		
	}
}
