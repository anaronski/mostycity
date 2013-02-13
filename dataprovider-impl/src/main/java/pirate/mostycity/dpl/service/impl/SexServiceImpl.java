package pirate.mostycity.dpl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.ISexDao;
import pirate.mostycity.dpl.entity.Sex;
import pirate.mostycity.dpl.service.SexService;
import pirate.mostycity.exception.ServiceException;

@Service("sexService")
@Transactional (readOnly=false)
public class SexServiceImpl extends BaseServiceImpl<Sex, Long> implements SexService{

	@Autowired
	private ISexDao sexDao;
	
	
	@Override
	protected IBaseDao<Sex, Long> getDao() throws ServiceException {
		
		return checkDao(sexDao);
	}

}
