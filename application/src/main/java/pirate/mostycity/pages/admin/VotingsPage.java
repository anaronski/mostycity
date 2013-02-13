package pirate.mostycity.pages.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.CustomFeedbackPanel;
import pirate.mostycity.components.VotingsPanel;
import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.entity.VotingVariant;
import pirate.mostycity.dpl.service.VotingService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ResourceHelper;

public class VotingsPage extends AdminMainPage{
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(VotingsPage.class);

	@SpringBean
	private VotingService votingService;
	
	private WebMarkupContainer previewNewVoting;
	private Form<ValueMap> votingsForm;
	private WebMarkupContainer addNewVotingPanel;
	private Voting voting;
	private VotingVariant newVotingVariant;
	private CustomFeedbackPanel feedback;
	private String newVotingName, answer;
	private List<VotingVariant> newVotingVariants = new ArrayList<VotingVariant>();
	private RequiredTextField<String> answerTF;
	private RequiredTextField<String> questionTF;
	private int i = -1;
	private VotingsPanel votingPanel;
	
	public VotingsPage() {
		super();
	}

	@Override
	protected void initComponents() {
		feedback = new CustomFeedbackPanel("feedback");
		add(feedback.setOutputMarkupId(true));
		
		votingsForm = new Form<ValueMap>("votingsForm");
		votingsForm.setOutputMarkupId(true);
		add(votingsForm);
		RadioGroup<Voting> votingRadioGroup = new RadioGroup<Voting>(
				"votingRadioGroup", new PropertyModel<Voting>(this, "voting"));

		votingPanel = new VotingsPanel("votingsListPanel");
		votingPanel.setOutputMarkupId(true);
		votingRadioGroup.add(votingPanel);
		votingsForm.add(votingRadioGroup);	
		
		votingPanel.add(new SubmitLink("activateBtn"){

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit() {
				
				if(voting!=null){
					List<Voting> votingList = votingService.getActiveVotings();
					for(Voting voting : votingList){
						voting.setActiveFlag(false);
						try {
							votingService.update(voting);
						} catch (ServiceException e) {
							log.error(e.getMessage());
						}
					}
					voting.setActiveFlag(true);
					
					try {
						votingService.update(voting);
					} catch (ServiceException e) {
						log.error(e.getMessage());
					}
					votingPanel.setList(votingService.getAllVotings());
					
					feedback.info(ResourceHelper.getString("votingPage.mess.successActivate", this));
				}else{
					feedback.info(ResourceHelper.getString("votingPage.mess.selectVoting", this));
				}
			}
		});
		
//		votingPanel.add(new SubmitLink("deactivateBtn"){
//
//			private static final long serialVersionUID = 1L;
//			
//			@Override
//			public void onSubmit() {
//				
//				if(voting!=null){
//					voting.setActiveFlag(false);
//					votingService.update(voting);
//					
//					feedback.info(ResourceHelper.getString("votingPage.mess.successDeactivate", this));
//				}else{
//					feedback.info(ResourceHelper.getString("votingPage.mess.selectVoting", this));
//				}
//			}
//		});
		
		votingPanel.add(new SubmitLink("deleteBtn"){

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit() {
				
				if(voting!=null){
					if(!voting.getActiveFlag()){
						votingService.deleteVoting(voting);
						votingPanel.setList(votingService.getAllVotings());
						
						feedback.info(ResourceHelper.getString("votingPage.mess.successDeleted", this));
					}else {
						feedback.error(ResourceHelper.getString("votingPage.mess.deleteActiveVoting", this));
					}
				}else{
					feedback.info(ResourceHelper.getString("votingPage.mess.selectVoting", this));
				}
			}
		});
		
		addNewVotingPanel = new WebMarkupContainer("addNewVotingPanel");
		add(addNewVotingPanel);
		addNewVotingPanel.setOutputMarkupId(true);
		Form<ValueMap> newVotingForm = new Form<ValueMap>("newVotingForm");
		newVotingForm.setOutputMarkupId(true);
		Form<ValueMap> newVariantForm = new Form<ValueMap>("newVariantForm");
		newVariantForm.setOutputMarkupId(true);
		addNewVotingPanel.add(newVotingForm);
		addNewVotingPanel.add(newVariantForm);
		
		questionTF = new RequiredTextField<String>("question", new PropertyModel<String>(this, "newVotingName"));
		newVotingForm.add(questionTF.setOutputMarkupId(true));
		
		newVotingForm.add(new AjaxSubmitLink("setVotingName"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				
				target.add(previewNewVoting);
				target.add(feedback);
			}

		});
		answerTF = new RequiredTextField<String>("answer", new PropertyModel<String>(this, "answer"));
		answerTF.setOutputMarkupId(true);
		newVariantForm.add(answerTF);
		newVariantForm.add(new AjaxSubmitLink("addNewVariant"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				
				VotingVariant newVotingVariant = new VotingVariant();
				newVotingVariant.setAnswersCount(0l);
				newVotingVariant.setVariantName(answer);
				if(i!=-1){
					newVotingVariant.setVariantName(answerTF.getValue());
					newVotingVariants.set(i, newVotingVariant);
				}else{
					newVotingVariants.add(newVotingVariant);
				}
				answer = "";
				i = -1;
				target.add(previewNewVoting);
				target.add(answerTF);
				target.add(feedback);
			}
		});
		
		previewNewVoting = new WebMarkupContainer("previewNewVoting");
		previewNewVoting.setOutputMarkupId(true);
		addNewVotingPanel.add(previewNewVoting);
		
		previewNewVoting.add(new Label("newVotingName", new PropertyModel<String>(this, "newVotingName")));
		
		RadioGroup<VotingVariant> newVotingVariantRadioGroup = new RadioGroup<VotingVariant>(
				"votingVariantRadioGroup", new PropertyModel<VotingVariant>(this, "newVotingVariant"));
		NewVotingVariantsListView votingVariantsListView = new NewVotingVariantsListView("newVotingVariantListView", 
				new PropertyModel<List<VotingVariant>>(this, "newVotingVariants"), 100);
		newVotingVariantRadioGroup.add(votingVariantsListView);
		previewNewVoting.add(newVotingVariantRadioGroup);
		
		previewNewVoting.add(new AjaxLink<Void>("addNewVoting"){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(newVotingName==null || newVotingName.equals("") || newVotingName.equals(" ")){
					feedback.error(ResourceHelper.getString("votingPage.mess.nullQuestion", this));
				}else{
					if(newVotingVariants.size()<2){
						feedback.error(ResourceHelper.getString("votingPage.mess.smallVariants", this));
					}else{
						votingService.createNewVoting(getCurrentAccount().getId(), newVotingName, newVotingVariants);
						
						answer = "";
						newVotingName = "";
						newVotingVariants = new ArrayList<VotingVariant>();
						votingPanel.setList(votingService.getAllVotings());
						i = -1;
						feedback.info(ResourceHelper.getString("votingPage.mess.successAdded", this));
						target.add(previewNewVoting);
						target.add(answerTF);
						target.add(questionTF);
						target.add(votingsForm);
						target.add(votingPanel);
					}
				}
				target.add(feedback);
			}
			
			@Override
			public boolean isVisible() {
				
				return newVotingName!=null && !newVotingName.equals("") && !newVotingName.equals(" ") && newVotingVariants.size()>=2;
			}
		});
	}	

	private class NewVotingVariantsListView extends PageableListView<VotingVariant> implements Constants{

		private static final long serialVersionUID = 1L;

		public NewVotingVariantsListView(String id, IModel<List<VotingVariant>> model, int rowsPerPage) {
			super(id, model, rowsPerPage);
		}

		@Override
		protected void populateItem(final ListItem<VotingVariant> item) {
			final VotingVariant variant = item.getModelObject();
			
			item.add(new Radio<VotingVariant>("votingVariantChoice", new Model<VotingVariant>(variant)) {

				private static final long serialVersionUID = 1L;

				@Override
				protected boolean getStatelessHint() {

					return true;
				}
			});
			AjaxLink<Void> newVariantLink = new AjaxLink<Void>("newVariantLink") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					answer = variant.getVariantName();
					i = item.getIndex();
					
					target.add(answerTF);
				}
			};
			item.add(newVariantLink.add(new Label("votingVariantName", variant.getVariantName())));
		}
	}
	
	public Voting getVoting() {
		return voting;
	}

	public void setVoting(Voting voting) {
		this.voting = voting;
	}

	public String getNewVotingName() {
		return newVotingName;
	}

	public void setNewVotingName(String newVotingName) {
		this.newVotingName = newVotingName;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<VotingVariant> getNewVotingVariants() {
		return newVotingVariants;
	}

	public void setNewVotingVariants(List<VotingVariant> newVotingVariants) {
		this.newVotingVariants = newVotingVariants;
	}

	public VotingVariant getNewVotingVariant() {
		return newVotingVariant;
	}

	public void setNewVotingVariant(VotingVariant newVotingVariant) {
		this.newVotingVariant = newVotingVariant;
	}
	
}
