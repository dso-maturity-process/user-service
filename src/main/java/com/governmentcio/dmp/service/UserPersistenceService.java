package com.governmentcio.dmp.service;

import java.util.List;

import com.governmentcio.dmp.dao.RoleDao;
import com.governmentcio.dmp.dao.UserDao;
import com.governmentcio.dmp.service.impl.UserPersistenceServiceImpl;

/**
 * 
 * @author William Drew
 * @version 1.0
 * @since 1.0
 * @see UserPersistenceServiceImpl
 */
public interface UserPersistenceService {

	/**
	 * Create and persist a {@link UserDao} in the database.
	 * 
	 * @param userDao
	 */
	void create(UserDao userDao);

	/**
	 * Delete the user from the database.
	 * 
	 * @param userDao
	 */
	void delete(UserDao userDao);

	/**
	 * Return all the users stored in the database.
	 * 
	 * @return
	 */
	List<UserDao> getAll();

	/**
	 * Return the user having the name.
	 * 
	 * @param name
	 * @return
	 */
	UserDao getByName(String name);

	/**
	 * Return the user with emailAddress and password.
	 * 
	 * @param emailAddress
	 * @param password
	 * 
	 * @param email
	 * @return
	 */
	UserDao getByEmailAndPassword(String emailAddress, String password);

	/**
	 * Return the user having the passed id.
	 * 
	 * @param id
	 * @return
	 */
	UserDao getById(long id);

	/**
	 * Update the passed user in the database.
	 * 
	 * @param userDao
	 */
	void update(UserDao userDao);

	/**
	 * @param roleDao
	 * @return
	 */
	RoleDao createRole(RoleDao roleDao);

}