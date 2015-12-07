package com.metacube.metice.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @name AbstractDao
 * @description : This is abstract generic class which performs all hibernate
 *              CRUD operation of persistant class into database
 * @author Banwari
 *
 * @param <PK>
 *            : primary key field of persistent class
 * @param <T>
 *            : name of pesrsitent class type
 * @date 03-Dec-2015
 */
public abstract class AbstractDao<PK extends Serializable, T> {
	/* Generic class type varible */
	private final Class<T> persistentClass;

	/* default constructor which makes generic class to specific persitent class */
	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
	}

	/* SessionFactory class object */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * This method for getting hibernate Session object reference
	 * 
	 * @return current session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * This method for getting persistent object from database for primary key
	 * primary key
	 * 
	 * @param key
	 *            : Primary Key type of persistent class
	 * @return persistent object
	 */
	@SuppressWarnings("unchecked")
	public T getByKey(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	/**
	 * This method save object into database
	 * 
	 * @param entity
	 *            : is object which have to be save into the database
	 * @throws HibernateException
	 */
	public void save(T entity) throws HibernateException {
		getSession().save(entity);
	}
	
	/**
	 * This method persist object into database
	 * 
	 * @param entity
	 *            : is object which have to be persist into the database
	 * @throws HibernateException
	 */
	public void persist(T entity) throws HibernateException {
		getSession().persist(entity);
	}

	/**
	 * This method saveOrUpdate object into database
	 * 
	 * @param entity
	 *            : is object which have to be save or update into the database
	 * @throws HibernateException
	 */
	public void saveOrUpdate(T entity) throws HibernateException {
		getSession().saveOrUpdate(entity);
	}
	/**
	 * This method delete object from database
	 * 
	 * @param entity
	 *            : is object which have to be delete from the database
	 * @throws HibernateException
	 */
	public void delete(T entity) {
		getSession().delete(entity);
	}
	/**
	 * This method update object into database
	 * 
	 * @param entity
	 *            : is object which have to be update into the database
	 * @throws HibernateException
	 */
	public void update(T entity) throws HibernateException{
		getSession().update(entity);
	}
    /**
     * This method for getting Criteria object refernce
     * @return Criteria for the persistentClass
     */
	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

}
