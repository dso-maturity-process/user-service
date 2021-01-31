/**
 * 
 */
package com.governmentcio.dmp.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.governmentcio.dmp.dao.AccessCredentialDao;
import com.governmentcio.dmp.dao.RoleDao;
import com.governmentcio.dmp.exception.UserServiceException;
import com.governmentcio.dmp.model.Role;
import com.governmentcio.dmp.model.User;
import com.governmentcio.dmp.service.UserService;
import com.governmentcio.dmp.utility.ServiceHealth;

/**
 * 
 * @author <a href=mailto:support@governmentcio.com>support</a>
 *
 */
@RestController
@RequestMapping("/user")
public class UserServiceController {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(UserServiceController.class.getName());

	private UserService userService;

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 */
	@PostMapping("/login")
	public AccessCredentialDao login(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		return userService.login(userName, password);
	}

	/**
	 * 
	 */
	@GetMapping("/getUser/{userName}")
	public User getUserByName(@PathVariable String userName) {
		if ((userName == null) || (userName.length() == 0)) {
			throw new IllegalArgumentException(
					"User name parameter was null or empty");
		}
		return userService.getUserByName(userName);
	}

	/**
	 * 
	 */
	@GetMapping("/allUsers")
	public Iterable<User> getUsers() {

		return userService.getUsers();
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public User addUser(
			@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		return userService.addUser(firstName, lastName, userName, password);
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/addUserToRole", method = RequestMethod.POST)
	public void addUserToRole(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "roleType", required = true) String roleType,
			@RequestParam(value = "projectId", required = true) Long projectId)
			throws Exception {

		userService.addUserToRole(userName, roleType, projectId);
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/removeUserFromRole", method = RequestMethod.POST)
	public void removeUserFromRole(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "roleType", required = true) String roleType)
			throws Exception {

		userService.removeUserFromRole(userName, roleType);
	}

	/**
	 * 
	 */
	@DeleteMapping("/removeUser/{userName}")
	public void removeUser(@PathVariable String userName) {
		userService.removeUser(userName);
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public User updateUser(@RequestBody(required = true) User user)
			throws UserServiceException {
		return userService.updateUser(user);
	}

	// ********* Role functionality *********
	// ********* Role functionality *********
	// ********* Role functionality *********

	/**
	 * 
	 */
	@GetMapping("/getRole/{type}")
	public Role getRoleByType(@PathVariable String type) {
		return userService.getRoleByType(type);
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public Role addRole(
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = true) String description) {

		return userService.addRole(type, name, description);
	}

	/**
	 * 
	 */
	@DeleteMapping("/removeRole/{roleType}")
	public void removeRole(@PathVariable String roleType) {
		userService.removeRole(roleType);
	}

	/**
	 * 
	 */
	@GetMapping("/allRoles")
	public Iterable<RoleDao> getRoles() {
		return userService.getRoles();
	}

	/**
	 * 
	 */
	@GetMapping("/getUserRolesByProject")
	public Iterable<Role> getUserProjectRoles(
			@RequestParam(value = "userId", required = true) Long userId,
			@RequestParam(value = "projectId", required = true) Long projectId) {
		return userService.getUserRolesByProject(userId, projectId);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/healthz")
	public ServiceHealth healthz() {

		LOG.info("Checking health...");

		return new ServiceHealth("User", true); // TODO: Replace canned response.
	}

}
