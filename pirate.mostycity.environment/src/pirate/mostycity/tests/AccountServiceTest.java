package pirate.mostycity.tests;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pirate.mostycity.dpl.dao.IAccountStatusDao;
import pirate.mostycity.dpl.dao.IAccountTypeDao;
import pirate.mostycity.dpl.dao.ISexDao;
import pirate.mostycity.dpl.entity.Account;
import pirate.mostycity.dpl.entity.AccountInfo;
import pirate.mostycity.dpl.entity.UserAuth;
import pirate.mostycity.dpl.service.AccountService;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.exception.ServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class AccountServiceTest extends BaseServiseTest<Account, Long>{
	
	@Autowired
	private ISexDao sexDao;
	
	@Autowired
	private IAccountStatusDao accountStatusDao;
	
	@Autowired
	private IAccountTypeDao accountTypeDao;
	
	@Autowired
	private AccountService service;
	
	private Long id = 10l;
	
	@Before
	@Override
	public void createNewEntity() {
		
		entity =  new Account();
		entity.setId(id);
		entity.setAccountStatus(accountStatusDao.get(1l));
		entity.setAccountType(accountTypeDao.get(1l));
		entity.setLastLoginTs(new Date());
		entity.setCreateTs(new Date());
		entity.setLastUpdateAccId(1l);
	}
	
	@Override
	protected void modifiedEntity() {

		entity.setFirstName("dksjs");
	}
	
	@Test
	public void crud() throws ServiceException{
		super.crudTest(id);
	}
	
	@Test
	public void createUpdateDeleteAccount(){
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setSex(sexDao.get(1l));
		accountInfo.setEmail("email");
		
		UserAuth userAuth = new UserAuth();
		userAuth.setLogin("loogin");
		userAuth.setPassword("password");
		
		entity.setAccountInfo(accountInfo);
		entity.setUserAuth(userAuth);
		
		service.createAccount(entity);
		
		entity.getAccountInfo().setAim("sdgd");
		service.updateAccount(entity, 1l);
		
		service.deleteAccount(entity, 1l);
		
		try {
			service.delete(entity);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected IBaseService<Account, Long> getService() {
		
		return service;
	}

}
