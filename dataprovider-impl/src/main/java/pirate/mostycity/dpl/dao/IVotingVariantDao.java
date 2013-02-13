package pirate.mostycity.dpl.dao;

import java.util.List;

import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.entity.VotingVariant;

public interface IVotingVariantDao  extends IBaseDao<VotingVariant, Long>{

	public List<VotingVariant> getList(Voting voting, int firstResult, int maxResults, String order);
	
}
