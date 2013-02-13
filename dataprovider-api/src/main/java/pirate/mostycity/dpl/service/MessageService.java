package pirate.mostycity.dpl.service;

import java.util.List;

import pirate.mostycity.dpl.entity.Message;
import pirate.mostycity.exception.ServiceException;

public interface MessageService extends IBaseService<Message, Long>{

//	public List<Message> getList(Long myAccountId, int messagesType);
	public List<Message> getDialogMessages(Long myAccountId, Long accountId);
	public int getNewMessagesCount(Long myAccountId);
	public List<Message> getNewDialogMessages(Long myAccountId, Long accountId);
	public void deleteMessage(Message message, Long account);
	void deleteMessages(Long account1, Long account2, Long myAccount);
	void setReadedtoList(List<Message> messages) throws ServiceException;
	List<Message> getDialogs(Long myAccountId);

}
