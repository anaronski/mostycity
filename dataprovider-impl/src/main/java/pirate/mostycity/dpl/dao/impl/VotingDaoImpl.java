package pirate.mostycity.dpl.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IVotingDao;
import pirate.mostycity.dpl.entity.Voting;

@Repository
public class VotingDaoImpl extends BaseDaoImpl<Voting, Long> implements IVotingDao{

	public VotingDaoImpl() {
		super(Voting.class);
		
	}

	@Override
	public List<Voting> getAllVotings() {
		
		return getSortedList("activeFlag");
	}

	@Override
	public List<Voting> getActiveVotings(int firstResult, int maxResults) {
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("activeFlag", true);
		
		return getByEqualProperty(null, properties, firstResult, maxResults, null);
	}

}
