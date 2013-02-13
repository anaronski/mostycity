package pirate.mostycity.components;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.panel.Panel;

public class SendMessageFormPanel extends Panel{
	
	private static final long serialVersionUID = 1L;
	
	private AbstractDefaultAjaxBehavior click;

	public SendMessageFormPanel(String id, AbstractDefaultAjaxBehavior behavior) {
		super(id);
		
		this.click = behavior;
		
		WebMarkupContainer editableDiv = new WebMarkupContainer("editableDiv");
		editableDiv.setOutputMarkupId(true);
		
		add(editableDiv);
		
		add(click);
		
		add(new WebMarkupContainer("sendMessageBtn"){
			
			private static final long serialVersionUID = 1L;

			
			@Override
			protected void onComponentTag(ComponentTag tag) {
				
				tag.put("onClick", "saveMessage('" + click.getCallbackUrl() + "');");
			}
		});
		
		add(new SmilesPanel("smilesPanel"));
		add(new ContextImage("smilesIcon", "images/smiles/smile.gif"));
		
	}

}
