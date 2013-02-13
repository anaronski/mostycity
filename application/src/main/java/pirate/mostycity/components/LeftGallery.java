package pirate.mostycity.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.FilesUtils;

public class LeftGallery extends Panel implements Constants{

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(LeftGallery.class);
	

	public LeftGallery(String id) {
		super(id);
		
		List<String> list = new ArrayList<String>();
		try {
			list = FilesUtils.getFilesNames(FULL_PATH_TO_GALLERY_FOLDER);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		add(new GalleryListView("galleryList", list));
	}

	private class GalleryListView extends ListView<String>{

		private static final long serialVersionUID = 1L;

		public GalleryListView(String id, List<? extends String> list) {
			super(id, list);
		}

		@Override
		protected void populateItem(ListItem<String> item) {
			String imgName = item.getModelObject();
			
			item.add(new ContextImage("img", SHORT_PATH_TO_GALLERY_FOLDER + imgName));
		}
		
	}
}
