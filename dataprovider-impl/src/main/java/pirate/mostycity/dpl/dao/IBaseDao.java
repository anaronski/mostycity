package pirate.mostycity.dpl.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

public interface IBaseDao<T, PK>{
	
	Class<? extends T> getEntityClass();

	public PK save(T newInstance);

    public T get(PK id);
    
    public T get(PK id, String... initFields);
    
    public void update(T transientObject);

    public void delete(T persistentObject);
    
    public List<T> findByEqProperty(String property, Object value);
    
    public List<T> findByLikeProperty(String property, String value);
    
    public List<T> findByLikeProperty(String property, String value, boolean exactBeginning);
    
    public boolean exists(PK pk);
    
    public int getCountByEqualProperty(Criteria criteria,  Map<String, Object> properties);
    
    public List<T> findAll();

}
