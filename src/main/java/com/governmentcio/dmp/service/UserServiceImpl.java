/**
 * 
 */
package com.governmentcio.dmp.service;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.governmentcio.dmp.dao.AccessCredentialDao;
import com.governmentcio.dmp.dao.DomainFactory;
import com.governmentcio.dmp.dao.RoleDao;
import com.governmentcio.dmp.dao.UserDao;
import com.governmentcio.dmp.dao.UserRoleDao;
import com.governmentcio.dmp.exception.UserServiceException;
import com.governmentcio.dmp.model.Role;
import com.governmentcio.dmp.model.User;
import com.governmentcio.dmp.repository.RoleRepository;
import com.governmentcio.dmp.repository.UserRepository;
import com.governmentcio.dmp.repository.UserRoleRepository;

/**
 * 
 * @author <a href=mailto:support@governmentcio.com>support</a>
 *
 */
@RestController
@RequestMapping("/user")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(UserServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#login(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public AccessCredentialDao login(final String userName,
			final String password) {

		if ((null == userName) || (userName.length() == 0)) {
			throw new IllegalArgumentException(
					"UserDao name parameter was null or empty");
		}
		if ((null == password) || (password.length() == 0)) {
			throw new IllegalArgumentException(
					"Password parameter was null or empty");
		}

		LOG.info("[" + userName + "] has logged on.");

		// create access code

		AccessCredentialDao ac = new AccessCredentialDao();

		return ac; // TODO: Replace canned response.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#getUserByName(java
	 * .lang.String)
	 */
	@Override
	public User getUserByName(@PathVariable String userName) {
		if ((userName == null) || (userName.length() == 0)) {
			throw new IllegalArgumentException(
					"User name parameter was null or empty");
		}
		UserDao userDao = userRepository.findByuserName(userName);

		User user = DomainFactory.createUser(userDao);

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.userservice.controller.UserService#getUsers()
	 */
	@Override
	public Iterable<User> getUsers() {

		Iterable<UserDao> iterableUserDaos = userRepository.findAll();

		Set<User> iterableUsers = new HashSet<User>();

		for (UserDao nextDao : iterableUserDaos) {
			User user = DomainFactory.createUser(nextDao);
			iterableUsers.add(user);
		}

		return iterableUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#addUser(java.lang.
	 * String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public User addUser(final String firstName, final String lastName,
			final String userName, final String password) {

		if (null == firstName) {
			throw new IllegalArgumentException("First name was null");
		}
		if (null == lastName) {
			throw new IllegalArgumentException("Last name was null");
		}
		if (null == userName) {
			throw new IllegalArgumentException("User name was null");
		}
		if (null == password) {
			throw new IllegalArgumentException("Password was null");
		}

		LOG.info("Adding user [" + firstName + " " + lastName + "].");

		UserDao userDao = new UserDao(firstName, lastName, userName, password);
		userDao.setCreatedTimestamp(new Date(System.currentTimeMillis()));

		UserDao newUserDao = userRepository.save(userDao);

		User user = DomainFactory.createUser(newUserDao);

		LOG.info("User [" + user + "] added.");

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#addUserToRole(java
	 * .lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional
	public void addUserToRole(final String userName, final String roleType,
			final Long projectId) throws Exception {

		if (null == userName) {
			throw new IllegalArgumentException("User name was null");
		}
		if (null == roleType) {
			throw new IllegalArgumentException("Role type was null");
		}

		LOG.info("Adding user [" + userName + " to role [" + roleType + "].");

		UserDao userDao = userRepository.findByuserName(userName);

		if (null == userDao) {
			throw new Exception("User ]" + userName + "] not found.");
		}

		RoleDao roleDao = roleRepository.findByType(roleType);

		if (null == roleDao) {
			throw new Exception("Role ]" + roleType + "] not found.");
		}

		UserRoleDao userRoleDao = new UserRoleDao(userDao, roleDao);

		userRoleDao.setProjectID(projectId);

		userRoleRepository.save(userRoleDao);

		userDao.addUserRoleDao(userRoleDao);

		userRepository.save(userDao);

		roleDao.addUserRole(userRoleDao);

		roleRepository.save(roleDao);

		User user = DomainFactory.createUser(userDao);

		LOG.info("Role [" + roleType + "] added for user [" + user + "].");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#removeUserFromRole
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void removeUserFromRole(final String userName, final String roleType)
			throws UserServiceException {

		if (null == userName) {
			throw new IllegalArgumentException("User name was null");
		}
		if (null == roleType) {
			throw new IllegalArgumentException("Role type was null");
		}

		LOG.info("Removing user [" + userName + " from [" + roleType + "] role.");

		UserDao userDao = userRepository.findByuserName(userName);

		if (null == userDao) {
			throw new UserServiceException("User ]" + userName + "] not found.");
		}

		RoleDao roleDao = roleRepository.findByType(roleType);

		if (null == roleDao) {
			throw new UserServiceException("Role ]" + roleType + "] not found.");
		}

		Set<UserRoleDao> userRoleDaos = userDao.getUserRoleDaos();

		for (UserRoleDao userRoleDao : userRoleDaos) {
			if (userRoleDao.getUserDao().equals(userDao)
					&& userRoleDao.getRoleDao().equals(roleDao)) {

				userDao.getUserRoleDaos().remove(userRoleDao);
				userRepository.save(userDao);

				roleDao.getUserRoleDaos().remove(userRoleDao);
				roleRepository.save(roleDao);

				userRoleRepository.delete(userRoleDao);
			}
		}

		LOG.info("User [" + userName + "] removed from [" + roleType + "].");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#removeUser(java.
	 * lang.String)
	 */
	@Override
	@Transactional
	public void removeUser(final String userName) {

		if ((userName == null) || (userName.length() == 0)) {
			throw new IllegalArgumentException(
					"User name parameter was null or empty");
		}

		UserDao userToDelete = userRepository.findByuserName(userName);

		if (null != userToDelete) {
			userRepository.delete(userToDelete);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#updateUser(com.
	 * governmentcio.dmp.model.User)
	 */
	@Override
	@Transactional
	public User updateUser(final User user) throws UserServiceException {

		if (null == user) {
			throw new IllegalArgumentException("User parameter was null");
		}

		UserDao userDao = userRepository.getOne(Long.valueOf(user.getId()));

		if (null == userDao) {
			throw new UserServiceException(
					"User [" + user + "] not found for update.");
		}

		userDao.setFirstName(user.getFirstName());
		userDao.setLastName(user.getLastName());
		userDao.setUserName(user.getUserName());
		userDao.setPassword(user.getPassword());

		userRepository.save(userDao);

		return DomainFactory.createUser(userDao);
	}

	// ********* Role functionality *********
	// ********* Role functionality *********
	// ********* Role functionality *********

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#getRoleByType(java
	 * .lang.String)
	 */
	@Override
	public Role getRoleByType(final String type) {
		if ((null == type) || (type.length() == 0)) {
			throw new IllegalArgumentException("Type parameter was null or empty");
		}

		return DomainFactory.createRole(roleRepository.findByType(type));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#addRole(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public Role addRole(final String type, final String name,
			@RequestParam(value = "description", required = true) String description) {

		if (null == type) {
			throw new IllegalArgumentException("Type was null");
		}
		if (null == name) {
			throw new IllegalArgumentException("Name was null");
		}
		if (null == description) {
			throw new IllegalArgumentException("Description was null");
		}

		LOG.info("Adding role [" + type + "].");

		RoleDao roleDao = new RoleDao(type, name, description);

		RoleDao newRoleDao = roleRepository.save(roleDao);

		Role role = DomainFactory.createRole(newRoleDao);

		LOG.info("Role [" + type + "] added.");

		return role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.governmentcio.dmp.userservice.controller.UserService#removeRole(java.
	 * lang.String)
	 */
	@Override
	@Transactional
	public void removeRole(final String roleType) {

		if ((roleType == null) || (roleType.length() == 0)) {
			throw new IllegalArgumentException("Role type was null or empty");
		}

		RoleDao roleToDelete = roleRepository.findByType(roleType);

		if (null != roleToDelete) {
			roleRepository.delete(roleToDelete);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.governmentcio.dmp.userservice.controller.UserService#getRoles()
	 */
	@Override
	public Iterable<RoleDao> getRoles() {
		return roleRepository.findAll();
	}

}
