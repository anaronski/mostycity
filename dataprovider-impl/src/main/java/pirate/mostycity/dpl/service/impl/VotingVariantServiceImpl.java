package pirate.mostycity.dpl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.IVotingVariantDao;
import pirate.mostycity.dpl.entity.VotingVariant;
import pirate.mostycity.dpl.service.VotingVariantService;
import pirate.mostycity.exception.ServiceException;


@Service("votingVariantService")
@Transactional (readOnly=false)
public class VotingVariantServiceImpl extends BaseServiceImpl<VotingVariant, Long> implements VotingVariantService{

	@Autowired
	private IVotingVariantDao votingVariantDao;
	
	@Override
	protected IBaseDao<VotingVariant, Long> getDao() throws ServiceException {
		
		return votingVariantDao;
	}
	
}
