package pirate.mostycity.dpl.dao.impl;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IUserAuthDao;
import pirate.mostycity.dpl.entity.UserAuth;

@Repository
public class UserAuthDaoImpl extends BaseDaoImpl<UserAuth, Long> implements IUserAuthDao{

	public UserAuthDaoImpl() {
		super(UserAuth.class);
	}

}
