package com.governmentcio.dmp.service;

import com.governmentcio.dmp.dao.AccessCredentialDao;
import com.governmentcio.dmp.dao.RoleDao;
import com.governmentcio.dmp.exception.UserServiceException;
import com.governmentcio.dmp.model.Role;
import com.governmentcio.dmp.model.User;

public interface UserService {

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	AccessCredentialDao login(String userName, String password);

	/**
	 * 
	 * @param userName
	 * @return
	 */
	User getUserByName(String userName);

	/**
	 * 
	 * @return
	 */
	Iterable<User> getUsers();

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param userName
	 * @param password
	 * @return
	 */
	User addUser(String firstName, String lastName, String userName,
			String password);

	/**
	 * 
	 * @param userName
	 * @param roleType
	 * @param projectId
	 * @throws Exception
	 */
	void addUserToRole(String userName, String roleType, Long projectId)
			throws Exception;

	/**
	 * 
	 * @param userName
	 * @param roleType
	 * @throws Exception
	 */
	void removeUserFromRole(String userName, String roleType) throws Exception;

	/**
	 * 
	 * @param userName
	 */
	void removeUser(String userName);

	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserServiceException
	 */
	User updateUser(User user) throws UserServiceException;

	/**
	 * 
	 * @param type
	 * @return
	 */
	Role getRoleByType(String type);

	/**
	 * 
	 * @param type
	 * @param name
	 * @param description
	 * @return
	 */
	Role addRole(String type, String name, String description);

	/**
	 * 
	 * @param roleType
	 */
	void removeRole(String roleType);

	/**
	 * 
	 * @return
	 */
	Iterable<RoleDao> getRoles();

	/**
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	Iterable<Role> getUserRolesByProject(final Long userId, final Long projectId);

}