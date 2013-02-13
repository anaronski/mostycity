package pirate.mostycity.dpl.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseDaoImpl<TYPE, PRIMARY_KEY extends Serializable> extends HibernateDaoSupport{
	
//	private Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	/*
	 * 
	 */
	private Class<TYPE> type;
	

	
	
	
	@Autowired
	public void init(SessionFactory sessionFactory) {
	    setSessionFactory(sessionFactory);
	}

	
	/**
	 * 
	 * @param type
	 */
    public BaseDaoImpl(Class<TYPE> clazz){
    	this.type = clazz;
    }
    
    public Class<TYPE> getEntityClass() {

		return type;
	}

    /**
     * 
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
	public PRIMARY_KEY save(TYPE o) {
        return (PRIMARY_KEY) getSession().save(o);
    }

    /**
     * 
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public TYPE get(PRIMARY_KEY id) {
        return (TYPE) getSession().get(type, id);
    }

    public TYPE get(PRIMARY_KEY id, String... initFields) {

    	TYPE object = get(id);
		initialize(object, initFields);
		return object;
	}
    
    /**
     * 
     * @param o
     */
    public void update(TYPE o) {
        getSession().update(o);
    }

    /**
     * 
     * @param o
     */
    public void delete(TYPE o) {
        getSession().delete(o);
    }

    /**
     * 
     * @param property
     * @param value
     * @return
     */
	public List<TYPE> findByEqProperty(String property, Object value) {

		return findByCriteria(Restrictions.eq(property, value));
	}

	/**
	 * 
	 * @param property
	 * @param value
	 * @param exactBeginning
	 * @return
	 */
	public List<TYPE> findByLikeProperty(String property, String value, boolean exactBeginning) {

		String searchString;
		if (exactBeginning) {
			searchString = value + "%";
		} else {
			searchString = "%" + value + "%";
		}

		return findByCriteria(Restrictions.like(property, searchString));
	}
	
	protected  List<TYPE> getByEqualProperty(Criteria criteria,  Map<String, Object> properties, int firstResult, int maxResults, String order){
		return getByEqualProperty(criteria, properties, firstResult, maxResults, order, true);
    }
	
	protected  List<TYPE> getByEqualProperty(Criteria criteria,  Map<String, Object> properties, int firstResult, int maxResults, String order, boolean desc){
		
		Criteria crit = criteria!=null?criteria:getSession().createCriteria(getEntityClass());
    	
    	for (final String key : properties.keySet()) { 
    		
    		if (key == null) continue;
    		
    		final String propertyName = key;
    		
    		final Object propertyValue = properties.get(key);
    		    	 
    		crit.add(Restrictions.eq(propertyName, propertyValue));
    	}
    	if(order!=null){
    		if(desc)
    			crit.addOrder(Order.desc(order));
    		else
    			crit.addOrder(Order.asc(order));
    	}
    	
    	return findByCriteria(firstResult, maxResults, crit);
	}
	
	public int getCountByEqualProperty(Criteria criteria,  Map<String, Object> properties){
		
		Criteria crit;
		
    	if(criteria!=null)
    		crit = criteria;
    	else 
    		crit = getSession().createCriteria(type);
    	
    	for (final String key : properties.keySet()) { 
    		
    		if (key == null) continue;
    		
    		final String propertyName = key;
    		
    		final Object propertyValue = properties.get(key);
    		    	 
    		crit.add(Restrictions.eq(propertyName, propertyValue));
    	}
    	
    	crit.setProjection(Projections.rowCount());
    	
    	return Integer.valueOf((crit.list().get(0).toString()));
    }

	/**
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public List<TYPE> findByLikeProperty(String property, String value) {

		return findByLikeProperty(property, value, false);
	}
	
	/**
	 * 
	 * @param criterion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TYPE> findByCriteria(Criterion... criterion) {

		Criteria crit = getSession().createCriteria(type);
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	protected List<TYPE> findByCriteria(int firstResult, int maxResults, Criteria criteria) {

		if (firstResult >= 0) {
			criteria.setFirstResult(firstResult);
		}
		if (maxResults > 0) {
			criteria.setMaxResults(maxResults);
		}
		return criteria.list();
	}
 
	
	@SuppressWarnings("unchecked")
	public List<TYPE> getSortedList(String order){
		Criteria criteria = getSession().createCriteria(type);
		criteria.addOrder(Order.desc(order));
		
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TYPE> findAll() {

		Criteria criteria = getSession().createCriteria(type);
		
		return criteria.list();
	}

	/**
	 * 
	 * @param pk
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean exists(PRIMARY_KEY pk) {

		TYPE entity = (TYPE) getSession().get(type, pk);
		return entity != null;
	}

	public void initialize(TYPE object, String... initFields) {

		if (object != null) {
			for (String fieldName : initFields) {
				if (fieldName == null) {
					Hibernate.initialize(object);
					continue;
				}

				try {
					Method method = type.getMethod("get"
							+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
					Hibernate.initialize(method.invoke(object));
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}