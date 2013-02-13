package pirate.mostycity.dpl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.IUserAuthDao;
import pirate.mostycity.dpl.entity.UserAuth;
import pirate.mostycity.dpl.service.UserAuthService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.util.Constants;


@Service("userAuthService")
@Transactional (readOnly=false)
public class UserAuthServiceImpl extends BaseServiceImpl<UserAuth, Long> implements UserAuthService, Constants{

	@Autowired
	private IUserAuthDao userAuthDao;
	
	
	@Override
	public UserAuth getByLogin(String login) {
		
		List<UserAuth> list = userAuthDao.findByEqProperty("login", login);
		if(list!=null && list.size()>0)
			return list.get(0);
		else
			return null;
	}

	@Override
	protected IBaseDao<UserAuth, Long> getDao() throws ServiceException {
		
		return checkDao(userAuthDao);
	}

}
