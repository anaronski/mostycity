package pirate.mostycity.pages.main;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import pirate.mostycity.pages.BasePage;

public class MainPage extends BasePage{

	private static final long serialVersionUID = 1L;

	public MainPage() {
		super(new PageParameters());
		add(new Label("pageTitle", "MostyCity"));
		add(new Header("header"));
		add(new HeaderMenu("headerMenu"));
		add(new Footer("footer"));
	}

}
