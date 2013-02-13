package pirate.mostycity.dpl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.dao.IMessageDao;
import pirate.mostycity.dpl.entity.Message;
import pirate.mostycity.dpl.service.MessageService;
import pirate.mostycity.exception.ServiceException;


@Service("messageService")
@Transactional (readOnly=false)
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements MessageService{

	@Autowired
	private IMessageDao messageDao;

	
	@Override
	protected IBaseDao<Message, Long> getDao() throws ServiceException {
		
		return checkDao(messageDao);
	}


	@Override
	public List<Message> getDialogs(Long myAccountId) {
		List<Message> allMessages = messageDao.getList(myAccountId, 1, firstResult, maxResult, "createTs");
		List<Message> list = new ArrayList<Message>();
		List<String> interlocutors = new ArrayList<String>();
		
		for(Message message: allMessages){
			if(!interlocutors.contains(message.getFromAccountId().getId() + "-" + message.getToAccountId())){
					list.add(message);
					interlocutors.add(message.getFromAccountId().getId() + "-" + message.getToAccountId());
					interlocutors.add(message.getToAccountId() + "-" + message.getFromAccountId().getId());
			}
		}
		
		return list;
	}

	@Override
	public List<Message> getDialogMessages(Long myAccountId, Long accountId) {
		
		return messageDao.getDialogMessages(myAccountId, accountId, firstResult, maxResult, "createTs");
	}
	
	@Override
	public void deleteMessages(Long account1, Long account2, Long myAccount) {
		
		List<Message> messages = getDialogMessages(account2, account1); 
		
		for(Message message : messages){
			deleteMessage(message, myAccount);
		}
	}

	@Override
	public void deleteMessage(Message message, Long account) {
		
		if(message.getDeleteAccountId().equals(0l)){
			message.setDeleteAccountId(account);
			message.setNewFlag(false);
			messageDao.update(message);
		}else{
			messageDao.delete(message);
		}
		
	}


	@Override
	public int getNewMessagesCount(Long myAccountId) {
		
		return messageDao.getNewMessagesCount(myAccountId);
	}


	@Override
	public void setReadedtoList(List<Message> messages) throws ServiceException {
		for(Message message : messages){
			message.setNewFlag(false);
			update(message);
		}
	}


	@Override
	public List<Message> getNewDialogMessages(Long myAccountId, Long accountId) {
		return messageDao.getNewDialogMessages(myAccountId, accountId, firstResult, maxResult, "createTs");
	}

}
