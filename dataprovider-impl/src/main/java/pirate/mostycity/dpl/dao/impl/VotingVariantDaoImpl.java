package pirate.mostycity.dpl.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IVotingVariantDao;
import pirate.mostycity.dpl.entity.Voting;
import pirate.mostycity.dpl.entity.VotingVariant;

@Repository
public class VotingVariantDaoImpl extends BaseDaoImpl<VotingVariant, Long> implements IVotingVariantDao{

	public VotingVariantDaoImpl() {
		super(VotingVariant.class);
	}

	public List<VotingVariant> getList(Voting voting, int firstResult, int maxResults, String order){
		
		List<VotingVariant> list = new ArrayList<VotingVariant>();
		Criteria criteria = getSession().createCriteria(getEntityClass());
		Map<String, Object> properties = new HashMap<String, Object>();
		
		properties.put("votingId", voting.getId());
		list = getByEqualProperty(criteria, properties, firstResult, maxResults, order);
		
		return list;
	}
}
