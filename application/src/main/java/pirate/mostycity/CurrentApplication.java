package pirate.mostycity;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import pirate.mostycity.pages.admin.UsersPage;
import pirate.mostycity.pages.error.ErrorPage;
import pirate.mostycity.pages.home.HomePage;
import pirate.mostycity.pages.messages.DialogPage;
import pirate.mostycity.pages.messages.MessagesListPage;
import pirate.mostycity.pages.mycabinet.AccountInfoPage;
import pirate.mostycity.pages.mycabinet.NewsByAccountPage;
import pirate.mostycity.pages.mycabinet.editaccount.ChangeAvatarPage;
import pirate.mostycity.pages.mycabinet.editaccount.ChangePasswordPage;
import pirate.mostycity.pages.mycabinet.editaccount.ConfirmDeletePage;
import pirate.mostycity.pages.mycabinet.editaccount.UpdateAccountPage;
import pirate.mostycity.pages.news.NewsItemErrorPage;
import pirate.mostycity.pages.news.NewsItemNewPage;
import pirate.mostycity.pages.news.NewsItemPage;
import pirate.mostycity.pages.news.NewsItemUpdatePage;
import pirate.mostycity.pages.news.NewsPage;
import pirate.mostycity.pages.photogallery.PhotogalleryPage;
import pirate.mostycity.pages.registration.ConfirmRegistrationPage;
import pirate.mostycity.pages.registration.ForgetPasswordPage;
import pirate.mostycity.pages.registration.LoginPage;
import pirate.mostycity.pages.registration.RegistrationPage;

public class CurrentApplication extends WebApplication {
	
	private String appHome;
	
	public CurrentApplication() {

	}
	
	@Override
	protected void init() {
		super.init();
		
		getRequestCycleSettings().setRenderStrategy(
	            IRequestCycleSettings.RenderStrategy.ONE_PASS_RENDER); 
		
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		
		mountPage("home", HomePage.class);
		mountPage("login", LoginPage.class);
		mountPage("registration", RegistrationPage.class);
		mountPage("registration/congratulation", ConfirmRegistrationPage.class);
		mountPage("articles", NewsPage.class);
		mountPage("articles/${newsItemId}", NewsItemPage.class);
		mountPage("articles/error", NewsItemErrorPage.class);
		
		mountPage("profile/#{accountId}", AccountInfoPage.class);
		mountPage("profile/edit/#{accountId}", UpdateAccountPage.class);
		mountPage("profile/change-password/#{accountId}", ChangePasswordPage.class);
		mountPage("profile/change-avatar/#{accountId}", ChangeAvatarPage.class);
		mountPage("profile/delete/#{accountId}", ConfirmDeletePage.class);
		mountPage("my-articles/${accountId}", NewsByAccountPage.class);
		
		mountPage("article/update/${newsItemId}", NewsItemUpdatePage.class);
		mountPage("article/new", NewsItemNewPage.class);
		mountPage("forget-password", ForgetPasswordPage.class);
		mountPage("admin", UsersPage.class);
		mountPage("photogallery", PhotogalleryPage.class);
		
		mountPage("dialogs", MessagesListPage.class);
		mountPage("dialogs/${interlocuterId}", DialogPage.class);
		
		
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	public CurrentSession newSession(Request request, Response response) {

		return new CurrentSession(request);
	}

	public static CurrentApplication get() {

		return (CurrentApplication) Application.get();
	}

	protected Page onRuntimeException() {
		return new ErrorPage();		
	}

	public String getAppHome() {
		return appHome;
	}

	public void setAppHome(String appHome) {
		this.appHome = appHome;
	}

}
