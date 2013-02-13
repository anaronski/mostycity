package pirate.mostycity.dpl.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IAccountDao;
import pirate.mostycity.dpl.dao.IAccountInfoDao;
import pirate.mostycity.dpl.dao.IAccountStatusDao;
import pirate.mostycity.dpl.dao.IAccountTypeDao;
import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.IUserAuthDao;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.AccountInfo;
import pirate.mostycity.dpl.entity.UserAuth;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.exception.ServiceException;
import pirate.mostycity.util.Constants;

@Service("accountService")
@Transactional (readOnly=false)
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService, Constants{

	@Autowired
	private IAccountDao accountDao;
	
	@Autowired
	private IAccountInfoDao accountInfoDao;
	
	@Autowired
	private IUserAuthDao userAuthDao;
	
	@Autowired
	private IAccountTypeDao accountTypeDao;
	
	@Autowired 
	private IAccountStatusDao accountStatusDao;
	
	
	public Account getAccount(Long id){
		return accountDao.get(id);
	}

	@Override
	protected IBaseDao<Account, Long> getDao() throws ServiceException {
		
		return checkDao(accountDao);
	}

	@Override
	public void createAccount(Account account) {
		
		Date currDate = new Date();
		
		account.setLastLoginTs(currDate);
		account.setCreateTs(currDate);
		account.setAccountType(accountTypeDao.get(ACCOUNT_TYPE_USER));
		account.setAccountStatus(accountStatusDao.get(ACCOUNT_STATUS_ACTIVE));
		account.setLastUpdateAccId(1l);
		Long id = accountDao.save(account);
		
		account.setLastUpdateAccId(id);
		accountDao.update(account);
		
		AccountInfo accountInfo = account.getAccountInfo();
		accountInfo.setId(id);
		accountInfoDao.save(accountInfo);
		
		UserAuth userAuth = account.getUserAuth();
		userAuth.setId(id);
		userAuthDao.save(userAuth);
	}

	@Override
	public Account updateAccount(Account account, Long lastUpdateAccId) {
		account.setLastUpdateAccId(lastUpdateAccId);
		accountDao.update(account);
		accountInfoDao.update(account.getAccountInfo());
		userAuthDao.update(account.getUserAuth());
		
		return account;
	}

	@Override
	public Account deleteAccount(Account account, Long lastUpdateAccId) {
		account.getAccountStatus().setId(ACCOUNT_STATUS_DELETED);
		account.setLastUpdateAccId(lastUpdateAccId);
		accountDao.update(account);
		
		return account;
	}

	@Override
	public Account activateAccount(Account account, Long lastUpdateAccId) {
		account.setAccountStatus(accountStatusDao.get(ACCOUNT_STATUS_ACTIVE));
		account.setLastUpdateAccId(lastUpdateAccId);
		accountDao.update(account);
		
		return account;
	}

	@Override
	public Account deactivateAccount(Account account, Long lastUpdateAccId) {
		account.setAccountStatus(accountStatusDao.get(ACCOUNT_STATUS_INACTIVE));
		account.setLastUpdateAccId(lastUpdateAccId);
		accountDao.update(account);
		
		return account;
	}

	@Override
	public List<Account> getAllAccounts() {
		
		return accountDao.findAll();
	}
	
	
}
