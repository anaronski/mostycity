package pirate.mostycity.pages.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pirate.mostycity.components.LeftGallery;
import pirate.mostycity.components.WelcomePanel;
import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.entity.VotingVariant;
import pirate.mostycity.dpl.service.VotingService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.pages.registration.Login;
import pirate.mostycity.pages.registration.LoginPage;
import pirate.mostycity.util.Constants;
import pirate.mostycity.utils.ResourceHelper;

public class MainPageLP extends MainPage{
	
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(MainPageLP.class);
	
	private WebMarkupContainer leftpanel;
	private List<Voting> votingList;
	private Form<ValueMap> votingForm;
	private VotingPanel votingPanel;
	private boolean visible = true;
	private WelcomePanel welcomePanel;
	
	@SpringBean
	private VotingService votingService;
	
	
	/**
	 * 
	 */
	public MainPageLP() {
		super();
		leftpanel = new WebMarkupContainer("leftPanel");
		WebMarkupContainer loginPanel = new Login("loginPanel"){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onWrongLogin(String message) {
				setResponsePage(LoginPage.class, new PageParameters());
				error(message);
			}
		};
		leftpanel.add(loginPanel);
		loginPanel.setVisible(!isAuth());
		welcomePanel = new WelcomePanel("welcomePanel");
		welcomePanel.setVisible(isAuth());
		leftpanel.add(welcomePanel);
		leftpanel.add(new LeftGallery("gallery"));
		
		votingForm = new Form<ValueMap>("votingForm");
		votingForm.setOutputMarkupId(true);
		leftpanel.add(votingForm);
		votingList = votingService.getActiveVotings();
		votingPanel = new VotingPanel("votingPanel");
		votingPanel.setOutputMarkupId(true);
		votingPanel.setVisible(votingList!=null && votingList.size()>0);
		votingForm.add(votingPanel);
		
		add(leftpanel);
	}

	public class VotingPanel extends WebMarkupContainer implements Constants{

		private static final long serialVersionUID = 1L;
		
		private VotingVariant votingVariant;
		private List<VotingVariant> votingVariants = new ArrayList<VotingVariant>();
		private VotingVariantsListView votingVariantsListView;
		private VotingVariantsResultView votingVariantsResultView;
		private RadioGroup<VotingVariant> votingVariantRadioGroup;
		private Voting voting = new Voting();
		private String answersCount = "";
		
		public VotingPanel(String id) {
			super(id);
			setOutputMarkupId(true);
			
			votingList = votingService.getActiveVotings();
			if(votingList.size()>0)
				voting = votingList.get(0);
			if(voting!=null){
				votingVariants = votingService.getVotingVariants(voting);
				answersCount = voting.getAnswersCount()!=null ? voting.getAnswersCount().toString() : "";
			}			
			add(new Label("votingName", new PropertyModel<String>(voting, "votingName")));
			add(new Label("answersCount", new PropertyModel<String>(this, "answersCount")));
			
			votingVariantRadioGroup = new RadioGroup<VotingVariant>(
					"votingVariantRadioGroup", new PropertyModel<VotingVariant>(this, "votingVariant"));
			votingVariantsListView = new VotingVariantsListView("votingVariantListView", 
					new PropertyModel<List<VotingVariant>>(this, "votingVariants"), 100);
			votingVariantRadioGroup.add(votingVariantsListView);
			add(votingVariantRadioGroup);
			
			votingVariantsResultView = new VotingVariantsResultView("votingVariantsResultView", 
					new PropertyModel<List<VotingVariant>>(this, "votingVariants"), 100);
			votingVariantsResultView.setVisible(false);
			votingVariantRadioGroup.add(votingVariantsResultView);
			
			add(new AjaxSubmitLink("voteBtn"){

				private static final long serialVersionUID = 1L;
				
				@Override
				public void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
					
					if(votingVariant!=null){
						votingService.vote(votingVariant);
						votingList = votingService.getActiveVotings();
						voting = votingList.get(0);
						votingVariants = votingService.getVotingVariants(voting);
						answersCount = voting.getAnswersCount().toString();
						visible = false;
						votingVariantsResultView.setVisible(true);
						votingVariantsListView.setVisible(false);
						target.add(votingPanel);
					}
				}
				
				@Override
				public boolean isVisible() {
					
					return visible;
				}
			});
		}
		
		public VotingVariant getVotingVariant() {
			return votingVariant;
		}

		public void setVotingVariant(VotingVariant votingVariant) {
			this.votingVariant = votingVariant;
		}

		public List<VotingVariant> getVotingVariants() {
			return votingVariants;
		}

		public void setVotingVariants(List<VotingVariant> votingVariants) {
			this.votingVariants = votingVariants;
		}

		public String getAnswersCount() {
			return answersCount;
		}

		public void setAnswersCount(String answersCount) {
			this.answersCount = answersCount;
		}
		
	}
	
	
	
	private class VotingVariantsResultView extends PageableListView<VotingVariant> implements Constants{

		private static final long serialVersionUID = 1L;

		public VotingVariantsResultView(String id, List<VotingVariant> list, int rowsPerPage) {
			super(id, list, rowsPerPage);
		}
		
		public VotingVariantsResultView(String id, IModel<List<VotingVariant>>  model, int rowsPerPage) {
			super(id, model, rowsPerPage);
		}

		@Override
		protected void populateItem(ListItem<VotingVariant> item) {
			VotingVariant variant = item.getModelObject();			
			Voting voting = new Voting();
			try {
				voting = votingService.read(variant.getVotingId());
			} catch (ServiceException e) {
				log.error(e.getMessage());
			}
			
			item.add(new Label("votingVariantName", variant.getVariantName()));
			item.add(new Label("votingResult", ResourceHelper.getString("votingPage.result", this, variant.getAnswersCount(),
					voting.getAnswersCount()!=0?variant.getAnswersCount()*100/voting.getAnswersCount():0)));
			Label percent = new Label("percent", " ");
			item.add(percent);
			percent.add(new AttributeAppender("style", new Model<String>("padding-right: "+
					(voting.getAnswersCount()!=0?variant.getAnswersCount()*100/voting.getAnswersCount():0)+"%;" +
							"border: 1px solid; background-color: #99FF99;"), " "));
		}
	}
	

	public WebMarkupContainer getLeftpanel() {
		return leftpanel;
	}


	public void setLeftpanel(WebMarkupContainer leftpanel) {
		this.leftpanel = leftpanel;
	}


	public List<Voting> getVotingList() {
		return votingList;
	}


	public void setVotingList(List<Voting> list) {
		this.votingList = list;
	}


	public WelcomePanel getWelcomePanel() {
		return welcomePanel;
	}


	public void setWelcomePanel(WelcomePanel welcomePanel) {
		this.welcomePanel = welcomePanel;
	}
	
	
}