package pirate.mostycity.dpl.dao;

import java.util.List;

import pirate.mostycity.dpl.entity.Voting;

public interface IVotingDao extends IBaseDao<Voting, Long>{

	List<Voting> getAllVotings();
	List<Voting> getActiveVotings(int first, int max);
}
