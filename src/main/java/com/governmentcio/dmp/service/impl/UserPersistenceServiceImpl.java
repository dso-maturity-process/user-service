/**
 * 
 */
package com.governmentcio.dmp.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.governmentcio.dmp.dao.RoleDao;
import com.governmentcio.dmp.dao.UserDao;
import com.governmentcio.dmp.service.UserPersistenceService;

/**
 * @author <a href=mailto:support@governmentcio.com>support
 *
 */
public class UserPersistenceServiceImpl implements UserPersistenceService {

	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 
	 */
	public UserPersistenceServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.service.UserPersistenceService#create(com.
	 * governmentcio .dmp.userservice.model.UserDao)
	 */
	@Override
	public void create(UserDao userDao) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.service.UserPersistenceService#delete(com.
	 * governmentcio .dmp.userservice.model.UserDao)
	 */
	@Override
	public void delete(UserDao userDao) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.service.UserPersistenceService#getAll()
	 */
	@Override
	public List<UserDao> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.service.UserPersistenceService#getByName(java.lang.
	 * String)
	 */
	@Override
	public UserDao getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.service.UserPersistenceService#getByEmailAndPassword(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public UserDao getByEmailAndPassword(String emailAddress, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.service.UserPersistenceService#getById(long)
	 */
	@Override
	public UserDao getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.service.UserPersistenceService#update(com.
	 * governmentcio .dmp.userservice.model.UserDao)
	 */
	@Override
	public void update(UserDao userDao) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.service.UserPersistenceService#createRole(com.
	 * governmentcio.dmp.userservice.model.RoleDao)
	 */
	@Override
	public RoleDao createRole(RoleDao roleDao) {
		// TODO Auto-generated method stub
		return null;
	}

}
