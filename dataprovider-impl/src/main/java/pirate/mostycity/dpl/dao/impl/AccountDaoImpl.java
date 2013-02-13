package pirate.mostycity.dpl.dao.impl;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IAccountDao;
import pirate.mostycity.dpl.entity.Account;

@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account, Long> implements IAccountDao{

	protected AccountDaoImpl() {
		super(Account.class);
	}
}
