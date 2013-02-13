package pirate.mostycity.tests;

import java.io.Serializable;

import org.junit.Before;

import pirate.mostycity.dpl.service.IBaseService;
import pirate.mostycity.exception.ServiceException;


public abstract class BaseServiseTest<T, S extends Serializable> {

	protected T entity;
	
	protected  IBaseService<T, S> service;

	@Before
	public abstract void createNewEntity() throws ServiceException;
	protected abstract void modifiedEntity();
	protected abstract IBaseService<T, S> getService();
	
	public void crudTest(S key) throws ServiceException{
		service = getService();
//		T entity = service.read(key);
		service.create(entity);
		service.read(key);
		modifiedEntity();
		service.update(entity);
		service.delete(entity);		
	}
	
}
