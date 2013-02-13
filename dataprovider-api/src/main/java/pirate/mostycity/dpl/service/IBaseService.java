package pirate.mostycity.dpl.service;

import java.io.Serializable;

import pirate.mostycity.exception.ServiceException;

public interface IBaseService <T, PK extends Serializable>{

	public Class<? extends T> getEntityClass() throws ServiceException;

	public PK create(T value) throws ServiceException;
    public void update(T value) throws ServiceException;
    public void delete(T value) throws ServiceException;
    public T read(PK key) throws ServiceException;
    public T read(PK key, String... initFields) throws ServiceException;
    public boolean exists(PK key) throws ServiceException;
}
