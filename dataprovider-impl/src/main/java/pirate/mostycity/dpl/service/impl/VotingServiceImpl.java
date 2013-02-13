package pirate.mostycity.dpl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.IVotingDao;
import pirate.mostycity.dpl.dao.IVotingVariantDao;
import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.entity.VotingVariant;
import pirate.mostycity.dpl.service.VotingService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.util.Constants;



@Service("votingService")
@Transactional (readOnly=false)
public class VotingServiceImpl extends BaseServiceImpl<Voting, Long> implements VotingService, Constants{

	@Autowired
	private IVotingDao votingDao;
	
	@Autowired
	private IVotingVariantDao votingVariantDao;
	
	
	@Override
	protected IBaseDao<Voting, Long> getDao() throws ServiceException {
		
		return votingDao;
	}

	@Override
	public List<Voting> getAllVotings() {
		
		return votingDao.getAllVotings();
	}
	
	@Override
	public List<VotingVariant> getVotingVariants(Voting voting) {
		
		return votingVariantDao.getList(voting, firstResult, 0, null);
	}

	@Override
	public List<Voting> getActiveVotings() {
		
		return votingDao.getActiveVotings(firstResult, 0);
	}

	@Override
	public void vote(VotingVariant votingVariant) {

		Voting voting = votingDao.get(votingVariant.getVotingId());
		votingVariant.setAnswersCount(votingVariant.getAnswersCount()+1);
		voting.setAnswersCount(voting.getAnswersCount()+1);
		
		votingDao.update(voting);
		votingVariantDao.update(votingVariant);
	}

	@Override
	public Long createNewVoting(Long id, String newVotingName, List<VotingVariant> newVotingVariants) {
		
		Voting newVoting = new Voting();
		
		newVoting.setAnswersCount(0l);
		newVoting.setCreateTs(new Date());
		newVoting.setVariantsCount(2);
		newVoting.setVotingName(newVotingName);
		newVoting.setAccountId(id);
		newVoting.setActiveFlag(false);
		
		Long votingId = votingDao.save(newVoting);
		
		for(VotingVariant variant: newVotingVariants){
			variant.setVotingId(votingId);
			votingVariantDao.save(variant);
		}
		
		return votingId;
	}

	@Override
	public void addNewVariant(Long votingId, String answer) {
		
		VotingVariant votingVariant = new VotingVariant();
		votingVariant.setAnswersCount(0l);
		votingVariant.setVariantName(answer);
		votingVariant.setVotingId(votingId);
		votingVariantDao.save(votingVariant);
	}

	@Override
	public void deleteVoting(Voting voting) {
		List<VotingVariant> votingVariants = new ArrayList<VotingVariant>();
		votingVariants = votingVariantDao.getList(voting, firstResult, 0, null);
		
		for(VotingVariant variant: votingVariants){
			votingVariantDao.delete(variant);
		}
		
		votingDao.delete(voting);
	}
}
