package pirate.mostycity.dpl.service;

import java.util.List;

import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.entity.VotingVariant;

public interface VotingService extends IBaseService<Voting, Long>{

	List<Voting> getAllVotings();
	List<Voting> getActiveVotings();
	List<VotingVariant> getVotingVariants(Voting voting);
	void vote(VotingVariant votingVariant);
	Long createNewVoting(Long accountId, String newVotingName, List<VotingVariant> newVotingVariants);
	void addNewVariant(Long votingId, String answer);
	void deleteVoting(Voting voting);
}
