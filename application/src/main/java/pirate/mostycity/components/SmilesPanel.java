package pirate.mostycity.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.cycle.RequestCycle;

import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.FilesUtils;

public class SmilesPanel extends Panel implements Constants{

	private static final long serialVersionUID = 1L;
	
	private AbstractDefaultAjaxBehavior click;
	


	public SmilesPanel(String id) {
		super(id);
		
		List<String> smilesUrls = new ArrayList<String>();
		try {
			smilesUrls = FilesUtils.getFilesNames(FULL_PATH_TO_SMILES_FOLDER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		add(new SmilesListView("smiles", smilesUrls));
		
//		((DialogPage)getPage()).getMessageTxt();
	}

	
	private class SmilesListView extends ListView<String> {
		
		private static final long serialVersionUID = 1L;

		public SmilesListView(String id, List<? extends String> list) {
			super(id, list);
		}
		
		@Override
		protected void populateItem(ListItem<String> item) {
			final String smileUrl = item.getModelObject();
			
			item.add(new WebMarkupContainer("smile") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onComponentTag(ComponentTag tag) {
					
					tag.put("src", "../" + SHORT_PATH_TO_SMILES_FOLDER + smileUrl);
					
//					tag.put("onClick", "callWicket('" + click.getCallbackUrl() + "');");
				}
			});
			
			click = new AbstractDefaultAjaxBehavior() {
				
				private static final long serialVersionUID = 1L;

				@Override
				protected void respond(AjaxRequestTarget target) {
					 String messageTxt = RequestCycle.get().getRequest().getUrl().getQueryParameter("message").getValue();
//					 RequestCycle.get().getRequest().getRequestParameters().getParameterValue("foo");
					 
					
				}
			};
			
		}
		
		
		
	}
}
