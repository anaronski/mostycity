package pirate.mostycity.pages.main;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import pirate.mostycity.dpl.entity.VotingVariant;
import pirate.mostycity.util.Constants;

public class VotingVariantsListView extends PageableListView<VotingVariant> implements Constants{

	private static final long serialVersionUID = 1L;

	public VotingVariantsListView(String id, IModel<List<VotingVariant>> model, int rowsPerPage) {
		super(id, model, rowsPerPage);
	}
	
	public VotingVariantsListView(String id, List<VotingVariant> list, int rowsPerPage) {
		super(id, list, rowsPerPage);
	}

	@Override
	protected void populateItem(ListItem<VotingVariant> item) {
		VotingVariant variant = item.getModelObject();
		
		item.add(new Radio<VotingVariant>("votingVariantChoice", new Model<VotingVariant>(variant)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected boolean getStatelessHint() {

				return true;
			}
		});
		item.add(new Label("votingVariantName", variant.getVariantName()));
	}
}