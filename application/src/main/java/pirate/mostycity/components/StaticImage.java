package pirate.mostycity.components;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

public class StaticImage extends WebComponent {

	private static final long serialVersionUID = 1L;

	public StaticImage(String id, IModel<String> urlModel) {
		super(id, urlModel);
	}

	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
		tag.put("src", getDefaultModelObjectAsString());
	}

}
