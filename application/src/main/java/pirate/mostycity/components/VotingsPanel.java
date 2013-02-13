package pirate.mostycity.components;

import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.entity.VotingVariant;
import pirate.mostycity.dpl.service.VotingService;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ResourceHelper;

public class VotingsPanel extends Panel implements Constants{
	
	private static final long serialVersionUID = 1L;

	private List<Voting> list;
	
	@SpringBean
	private VotingService votingService;
	
	
	public VotingsPanel(String id) {
		super(id);
		
		list = votingService.getAllVotings();
		VotingsListView votingsListView = new VotingsListView("votingsListView", new PropertyModel<List<Voting>>(this, "list"), 5);
		add(votingsListView);
		
		CustomPagingNavigator pagingNavigator = new CustomPagingNavigator("pagingNavigator", votingsListView){
			
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				
				return list.size()>5;
			}
		};
		
		add(pagingNavigator);
		
	}

	public class VotingsListView extends PageableListView<Voting> implements Constants{

		private static final long serialVersionUID = 1L;
		
		private VotingVariant votingVariant;
		
		public VotingsListView(String id, IModel<? extends List<? extends Voting>> model, int rowsPerPage) {
			super(id, model, rowsPerPage);
		}

		@Override
		protected void populateItem(ListItem<Voting> item) {
			Voting voting = item.getModelObject();
			List<VotingVariant> votingVariants = votingService.getVotingVariants(voting);
		
			PageParameters params = new PageParameters();
			params.add(VOTING_ID, voting.getId());
			
			item.add(new Radio<Voting>("votingChoice", new Model<Voting>(voting)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected boolean getStatelessHint() {

					return true;
				}
			});
			
			item.add(new Label("createTs", ResourceHelper.getString("common.created", this, DateFormatUtils.format(voting.getCreateTs(), "dd-MM-yy"))));
			
			item.add(new Label("votingName", voting.getVotingName()));
			item.add(new Label("answersCount", ResourceHelper.getString("common.answersCount", this, voting.getAnswersCount().toString())));
			item.add(new Label("votingId", ResourceHelper.getString("common.id", this, voting.getId().toString())));
			item.add(new Label("votingStatus", ResourceHelper.getString("common.status1", this, 
					voting.getActiveFlag()?ResourceHelper.getString("status.active", this):ResourceHelper.getString("status.inactive", this))));
			
			RadioGroup<VotingVariant> votingVariantRadioGroup = new RadioGroup<VotingVariant>(
					"votingVariantRadioGroup", new PropertyModel<VotingVariant>(this, "votingVariant"));
			votingVariantRadioGroup.add(new VotingVariantsListView("votingVariantListView", 
					votingVariants, 100, voting));
			item.add(votingVariantRadioGroup);
		}
		
		public VotingVariant getVotingVariant() {
			return votingVariant;
		}

		public void setVotingVariant(VotingVariant votingVariant) {
			this.votingVariant = votingVariant;
		}
	}
	
	private class VotingVariantsListView extends PageableListView<VotingVariant> implements Constants{

		private static final long serialVersionUID = 1L;
		private Voting voting;

		public VotingVariantsListView(String id, List<VotingVariant> list, int rowsPerPage, Voting pVoting) {
			super(id, list, rowsPerPage);
			voting = pVoting;
		}

		@Override
		protected void populateItem(ListItem<VotingVariant> item) {
			VotingVariant variant = item.getModelObject();
			
//			PageParameters params = new PageParameters();
//			params.put(VOTING_VARIANT_ID, variant.getId());
			
			item.add(new Radio<VotingVariant>("votingVariantChoice", new Model<VotingVariant>(variant)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected boolean getStatelessHint() {

					return true;
				}
			});
			item.add(new Label("votingVariantName", variant.getVariantName()));
			item.add(new Label("votingResult", ResourceHelper.getString("votingPage.result", this, variant.getAnswersCount(),
					voting.getAnswersCount()!=0?variant.getAnswersCount()*100/voting.getAnswersCount():0)));
		}
	}
	

	public List<Voting> getList() {
		return list;
	}

	public void setList(List<Voting> list) {
		this.list = list;
	}
}
