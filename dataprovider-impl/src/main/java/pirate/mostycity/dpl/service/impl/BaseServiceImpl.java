package pirate.mostycity.dpl.service.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pirate.mostycity.dpl.dao.IBaseDao;
import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.exception.ServiceException;


@Service
public abstract class BaseServiceImpl <T, PK extends Serializable> implements IBaseService<T, PK>{
	
	protected int maxResult = 500;
	protected int firstResult = 0;

	@Override
	public Class<? extends T> getEntityClass() throws ServiceException {
		return getDao().getEntityClass();
	}
	

	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public PK create(T value) throws ServiceException {
		return getDao().save(value);
	}

	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void update(T value) throws ServiceException {
		getDao().update(value);
	}

	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(T value) throws ServiceException {
		getDao().delete(value);
	}

	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public T read(PK key) throws ServiceException {
		return getDao().get(key);
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public T read(PK key, String... initFields) throws ServiceException {
		return getDao().get(key, initFields);
	}

	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean exists(PK key) throws ServiceException {
		return getDao().exists(key);
	}
	
	
	protected abstract IBaseDao<T, PK> getDao() throws ServiceException;
	
	protected IBaseDao<T, PK> checkDao(IBaseDao<T, PK> dao) throws ServiceException{
		
		if (dao == null) {
			throw new ServiceException("DAO hasn't set for this service!");
	}
		return dao;
	}
}
