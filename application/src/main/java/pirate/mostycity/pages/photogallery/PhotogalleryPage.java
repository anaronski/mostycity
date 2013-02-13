package pirate.mostycity.pages.photogallery;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.CustomPagingNavigator;
import pirate.mostycity.components.LeftGallery;
import pirate.mostycity.pages.main.MainPageLP;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.FilesUtils;

public class PhotogalleryPage extends MainPageLP implements Constants{
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(LeftGallery.class);
	
	
	private List<String> list;

	public PhotogalleryPage(PageParameters parameters) {
		super();
		
		list = new ArrayList<String>();
		try {
			list = FilesUtils.getFilesNames(FULL_PATH_TO_GALLERY_FOLDER);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		Form<Void> form = new Form<Void>("photogalleryForm");
		add(form);
		
		PhotogalleryListView listView = new PhotogalleryListView("photogalleryList", new PropertyModel<List<String>>(this, "list"), PHOTOS_PER_PAGE);
		form.add(listView);
		
		CustomPagingNavigator pagingNavigator = new CustomPagingNavigator("pagingNavigator", listView);
		
		form.add(pagingNavigator.setVisible(list.size()>PHOTOS_PER_PAGE));
	}

	
	private class PhotogalleryListView extends PageableListView<String> implements Constants{

		private static final long serialVersionUID = 1L;

		public PhotogalleryListView(String id, IModel<? extends List<? extends String>> model, int rowsPerPage) {
			super(id, model, rowsPerPage);
			
		}

		@Override
		protected void populateItem(ListItem<String> item) {
			
			String imgName = item.getModelObject();
			
			item.add(new AttributeModifier("rel", "prettyPhoto[g_main]"));
			item.add(new AttributeModifier("title", imgName));
			item.add(new AttributeModifier("href", "images/left_panel/"+imgName));
			item.add(new ContextImage("img", SHORT_PATH_TO_GALLERY_FOLDER + imgName));
		}

			
	}
}
