package pirate.mostycity.dpl.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pirate.mostycity.dpl.dao.IMessageDao;
import pirate.mostycity.dpl.entity.Message;


@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, Long> implements IMessageDao{

	protected MessageDaoImpl() {
		super(Message.class);
	}

	@Override
	public List<Message> getList(Long myAccountId, int messagesType, int firstResult, int maxResults, String order) {
		
		List<Message> list = new ArrayList<Message>();
		Criteria criteria = getSession().createCriteria(getEntityClass());
		
		if(messagesType==1){
				criteria.add(
					Restrictions.or(
						Restrictions.eq("toAccountId", myAccountId), 
						Restrictions.eq("fromAccountId.id", myAccountId)
					)
				);
				criteria.add(Restrictions.ne("deleteAccountId", myAccountId));
				
			
			if(order!=null)
				criteria.addOrder(Order.desc(order));
			
			list = findByCriteria(firstResult, maxResults,criteria);
		}else{
			Map<String, Object> properties = new HashMap<String, Object>();
			
			switch(messagesType){
				case 2:
					properties.put("toAccountId", myAccountId);
					properties.put("newFlag", true);
					break;
				case 3:
					properties.put("toAccountId", myAccountId);
					break;
				case 4:
					properties.put("fromAccountId.id", myAccountId);
					break;
			}
			
			list = getByEqualProperty(criteria, properties, firstResult, maxResults, order);
		}
			
		return list;
	}

	@Override
	public List<Message> getDialogMessages(Long myAccountId, Long accountId,
			int firstResult, int maxResults, String order) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass());
		
		criteria.add(
				Restrictions.and(
						Restrictions.or(
								Restrictions.and(
									Restrictions.eq("toAccountId", accountId), 
									Restrictions.eq("fromAccountId.id", myAccountId)
								), 
								Restrictions.and(
									Restrictions.eq("toAccountId", myAccountId), 
									Restrictions.eq("fromAccountId.id", accountId)
								)
						), 
						Restrictions.ne("deleteAccountId", myAccountId)
				)
		);
		if(order!=null)
			criteria.addOrder(Order.asc(order));
		
		return findByCriteria(firstResult, maxResults,criteria);
	}

	@Override
	public int getNewMessagesCount(Long myAccountId) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass());
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("toAccountId", myAccountId);
		properties.put("newFlag", true);
		
		return getCountByEqualProperty(criteria, properties);
	}

	@Override
	public List<Message> getNewDialogMessages(Long myAccountId, Long accountId,
			int firstResult, int maxResults, String order) {
		
		Criteria criteria = getSession().createCriteria(getEntityClass());
		
			Map<String, Object> properties = new HashMap<String, Object>();
			
			properties.put("toAccountId", myAccountId);
			properties.put("newFlag", true);
			properties.put("fromAccountId.id", accountId);
			
			List<Message> list = getByEqualProperty(criteria, properties, firstResult, maxResults, order, false);
			
		return list;
	}
	
}
