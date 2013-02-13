package pirate.mostycity.dpl.dao;

import java.util.List;

import pirate.mostycity.dpl.entity.Message;

public interface IMessageDao extends IBaseDao<Message, Long>{

	public List<Message> getList(Long myAccountId, int messagesType, int firstResult, int maxResults, String order);
	public List<Message> getDialogMessages(Long myAccountId, Long accountId, int firstResult, int maxResults, String order);
	public List<Message> getNewDialogMessages(Long myAccountId, Long accountId, int firstResult, int maxResults, String order);
	public int getNewMessagesCount(Long myAccountId);
}
