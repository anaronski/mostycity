package pirate.mostycity.dpl.dao.impl;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IAccountStatusDao;
import pirate.mostycity.dpl.entity.AccountStatus;

@Repository
public class AccountStatusDaoImpl extends BaseDaoImpl<AccountStatus, Long> implements IAccountStatusDao{

	public AccountStatusDaoImpl() {
		super(AccountStatus.class);
	}

}
