package pirate.mostycity.dpl.service;

import pirate.mostycity.dpl.entity.UserAuth;

public interface UserAuthService extends IBaseService<UserAuth, Long>{
	
	public UserAuth getByLogin(String login);

}
