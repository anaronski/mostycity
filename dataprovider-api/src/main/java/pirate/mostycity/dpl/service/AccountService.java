package pirate.mostycity.dpl.service;

import java.util.List;

import pirate.mostycity.dpl.entity.Account;

public interface AccountService extends IBaseService<Account, Long>{
	
	public void createAccount(Account account);
	public Account updateAccount(Account account, Long lastUpdateAccId);
	public Account deleteAccount(Account account, Long lastUpdateAccId);
	public Account activateAccount(Account account, Long lastUpdateAccId);
	public Account deactivateAccount(Account account, Long lastUpdateAccId);
	public List<Account> getAllAccounts();
}
