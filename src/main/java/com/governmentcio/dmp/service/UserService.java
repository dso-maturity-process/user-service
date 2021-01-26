package com.governmentcio.dmp.service;

import com.governmentcio.dmp.dao.AccessCredentialDao;
import com.governmentcio.dmp.dao.RoleDao;
import com.governmentcio.dmp.exception.UserServiceException;
import com.governmentcio.dmp.model.Role;
import com.governmentcio.dmp.model.User;

public interface UserService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#login(java.lang.
	 * String, java.lang.String)
	 */
	AccessCredentialDao login(String userName, String password);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#getUserByName(java
	 * .lang.String)
	 */
	User getUserByName(String userName);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.userservice.controller.UserService#getUsers()
	 */
	Iterable<User> getUsers();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#addUser(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String)
	 */
	User addUser(String firstName, String lastName, String userName,
			String password);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#addUserToRole(java
	 * .lang.String, java.lang.String, java.lang.Long)
	 */
	void addUserToRole(String userName, String roleType, Long projectId)
			throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#removeUserFromRole
	 * (java.lang.String, java.lang.String)
	 */
	void removeUserFromRole(String userName, String roleType) throws Exception;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#removeUser(java.
	 * lang.String)
	 */
	void removeUser(String userName);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#updateUser(com.
	 * governmentcio.dmp.model.User)
	 */
	User updateUser(User user) throws UserServiceException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#getRoleByType(java
	 * .lang.String)
	 */
	Role getRoleByType(String type);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#addRole(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	Role addRole(String type, String name, String description);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#removeRole(java.
	 * lang.String)
	 */
	void removeRole(String roleType);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.userservice.controller.UserService#getRoles()
	 */
	Iterable<RoleDao> getRoles();

}