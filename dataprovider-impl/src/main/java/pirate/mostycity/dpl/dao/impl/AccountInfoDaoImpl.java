package pirate.mostycity.dpl.dao.impl;

import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IAccountInfoDao;
import pirate.mostycity.dpl.entity.AccountInfo;

@Repository
public class AccountInfoDaoImpl extends BaseDaoImpl<AccountInfo, Long> implements IAccountInfoDao{

	public AccountInfoDaoImpl() {
		super(AccountInfo.class);
	}

}
