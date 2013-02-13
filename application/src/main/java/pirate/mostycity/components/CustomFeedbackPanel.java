package pirate.mostycity.components;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

/**
 * 
 *
 */
public class CustomFeedbackPanel extends FeedbackPanel {

	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("deprecation")
	public CustomFeedbackPanel(String id, ComponentFeedbackMessageFilter filter) {

		super(id, filter);

		WebMarkupContainer feedbackul = (WebMarkupContainer) get("feedbackul");
		if (feedbackul != null) {
			feedbackul.add(new AttributeAppender("class", true,
					new PropertyModel<String>(this, "cssClass"), ""));
		}
	}


	@SuppressWarnings("deprecation")
	public CustomFeedbackPanel(String id) {

		super(id);

		WebMarkupContainer feedbackul = (WebMarkupContainer) get("feedbackul");
		if (feedbackul != null) {
			feedbackul.add(new AttributeAppender("class", true,
					new PropertyModel<String>(this, "cssClass"), ""));
		}
	}

	
	public String getCssClass() {

		String cssClass = null;
		if (anyMessage()) {
			if (anyMessage(FeedbackMessage.ERROR)) {
				cssClass = "feedbackulERROR";
			} else if (anyMessage(FeedbackMessage.INFO)) {
				cssClass = "feedbackulINFO";
			} else {
				cssClass = "feedbackulNONE";
			}
		} else {
			cssClass = "feedbackulNONE";
		}
		return cssClass;
	}
}
