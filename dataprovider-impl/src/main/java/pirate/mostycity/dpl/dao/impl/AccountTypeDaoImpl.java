package pirate.mostycity.dpl.dao.impl;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IAccountTypeDao;
import pirate.mostycity.dpl.entity.AccountType;

@Repository
public class AccountTypeDaoImpl extends BaseDaoImpl<AccountType, Long> implements IAccountTypeDao{

	public AccountTypeDaoImpl() {
		super(AccountType.class);
	}

}
