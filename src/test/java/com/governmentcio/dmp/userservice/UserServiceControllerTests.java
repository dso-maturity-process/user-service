
package com.governmentcio.dmp.userservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.governmentcio.dmp.Application;
import com.governmentcio.dmp.model.Role;
import com.governmentcio.dmp.model.Role.RoleType;
import com.governmentcio.dmp.model.User;

/**
 * 
 * @author <a href=mailto:support@governmentcio.com>support</a>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceControllerTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private static final String BASE_URL = "/user";

	/**
	 * 
	 */
	@Test
	public void test_User_CRUD_Functionality() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		String firstName = "Colonel";
		String lastName = "Clueless";
		String userName = "colonelclueless@governmentcio.com";
		String password = "doh";

		String parameters = "?firstName=" + firstName + "&lastName=" + lastName;
		parameters = parameters + "&userName=" + userName + "&password=" + password;

		ResponseEntity<User> userResponse = restTemplate.exchange(
				createURLWithPort("/addUser" + parameters), HttpMethod.POST, entity,
				new ParameterizedTypeReference<User>() {
				});

		assertNotNull(userResponse);

		assertTrue(userResponse.getStatusCode() == HttpStatus.OK);

		User newUser = userResponse.getBody();

		assertNotNull(newUser);

		assertTrue(newUser.getFirstName().equals(firstName));
		assertTrue(newUser.getPassword().equals(password));
		assertTrue(newUser.getUserName().equals(userName));
		assertNotNull(newUser.getCreatedTimestamp());

		// Get the User just added

		userResponse = restTemplate.exchange(
				createURLWithPort("/getUser/" + newUser.getUserName()), HttpMethod.GET,
				entity, new ParameterizedTypeReference<User>() {
				});

		assertNotNull(userResponse);

		assertTrue(userResponse.getStatusCode() == HttpStatus.OK);

		newUser = userResponse.getBody();

		assertNotNull(newUser);

		assertTrue(newUser.getFirstName().equals(firstName));
		assertTrue(newUser.getPassword().equals(password));
		assertTrue(newUser.getUserName().equals(userName));
		assertNotNull(newUser.getCreatedTimestamp());

		// Update the user - switch first and last names

		newUser.setLastName(firstName);
		newUser.setFirstName(lastName);

		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

		// Prepare header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(acceptableMediaTypes);
		HttpEntity<User> updatedUserEntity = new HttpEntity<User>(newUser, headers);

		userResponse = restTemplate.exchange(createURLWithPort("/updateUser"),
				HttpMethod.POST, updatedUserEntity, User.class);

		assertNotNull(userResponse);

		assertTrue(userResponse.getStatusCode() == HttpStatus.OK);

		User updatedUser = userResponse.getBody();

		assertNotNull(updatedUser);

		assertTrue(updatedUser.getFirstName().equals(lastName));
		assertTrue(updatedUser.getLastName().equals(firstName));

		// Remove the User

		ResponseEntity<Void> responseVoid = restTemplate.exchange(
				createURLWithPort("/removeUser/" + newUser.getUserName()),
				HttpMethod.DELETE, entity, new ParameterizedTypeReference<Void>() {
				});

		assertNotNull(responseVoid);

		assertTrue(responseVoid.getStatusCode() == HttpStatus.OK);

		// Ensure User deleted

		userResponse = restTemplate.exchange(
				createURLWithPort("/getUser/" + newUser.getUserName()), HttpMethod.GET,
				entity, new ParameterizedTypeReference<User>() {
				});

		assertNotNull(userResponse);

		assertTrue(userResponse.getStatusCode() == HttpStatus.OK);

		User userShouldBeDeleted = userResponse.getBody();

		assertNull(userShouldBeDeleted);

	}

	/**
	 * 
	 */
	@Test
	public void test_Getting_all_users() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Iterable<User>> response = restTemplate.exchange(
				createURLWithPort("/allUsers"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<Iterable<User>>() {
				});

		assertNotNull(response);

	}

	/**
	 * 
	 */
	@Test
	@Transactional
	public void test_Adding_then_Removing_Role_from_User() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		String userName = "wdrew@governmentcio.com";

		ResponseEntity<User> response = restTemplate.exchange(
				createURLWithPort("/getUser/" + userName), HttpMethod.GET, entity,
				new ParameterizedTypeReference<User>() {
				});

		assertNotNull(response);

		assertTrue(response.getStatusCode() == HttpStatus.OK);

		User user = response.getBody();

		assertNotNull(user);

		assertTrue(user.getUserName().equals(userName));

		// Add User to Role

		String parameters = "?userName=" + user.getUserName() + "&roleType="
				+ RoleType.SECURITY_ANALYST.name() + "&projectId=1000";

		ResponseEntity<Void> responseVoid = restTemplate.exchange(
				createURLWithPort("/addUserToRole" + parameters), HttpMethod.POST,
				entity, new ParameterizedTypeReference<Void>() {
				});

		assertNotNull(responseVoid);

		assertTrue(responseVoid.getStatusCode() == HttpStatus.OK);

		// get User and check for Roles

		response = restTemplate.exchange(
				createURLWithPort("/getUser/" + user.getUserName()), HttpMethod.GET,
				entity, new ParameterizedTypeReference<User>() {
				});

		assertNotNull(response);

		assertTrue(response.getStatusCode() == HttpStatus.OK);

		User userWithRoles = response.getBody();

		assertNotNull(userWithRoles);

		Set<Role> roles = userWithRoles.getRoles();

		Iterator<Role> iter = roles.iterator();

		while (iter.hasNext()) {
			Role r = iter.next();
			assertTrue(
					r.getRoleType().name().equals(RoleType.SECURITY_ANALYST.name()));
		}

		// Get user roles for a project

		parameters = "?userId=" + user.getId() + "&projectId=1000";

		ResponseEntity<Iterable<Role>> responseRoles = restTemplate.exchange(
				createURLWithPort("/getUserProjectRoles" + parameters), HttpMethod.GET,
				entity, new ParameterizedTypeReference<Iterable<Role>>() {
				});

		assertNotNull(response);

		assertTrue(response.getStatusCode() == HttpStatus.OK);

		Iterable<Role> userRolesForProject = responseRoles.getBody();

		iter = userRolesForProject.iterator();

		while (iter.hasNext()) {
			Role r = iter.next();
			assertTrue(
					r.getRoleType().name().equals(RoleType.SECURITY_ANALYST.name()));
		}

		// Now remove the Role from the User

		parameters = "?userName=" + user.getUserName() + "&roleType="
				+ RoleType.SECURITY_ANALYST.name();

		responseVoid = restTemplate.exchange(
				createURLWithPort("/removeUserFromRole" + parameters), HttpMethod.POST,
				entity, new ParameterizedTypeReference<Void>() {
				});

		assertNotNull(responseVoid);

		assertTrue(responseVoid.getStatusCode() == HttpStatus.OK);

		// get User and check security Role has been removed

		response = restTemplate.exchange(
				createURLWithPort("/getUser/" + user.getUserName()), HttpMethod.GET,
				entity, new ParameterizedTypeReference<User>() {
				});

		assertNotNull(response);

		assertTrue(response.getStatusCode() == HttpStatus.OK);

		User userWithoutRoles = response.getBody();

		assertNotNull(userWithoutRoles);

		Set<Role> rolesMinusSecurity = userWithoutRoles.getRoles();

		iter = rolesMinusSecurity.iterator();

		while (iter.hasNext()) {
			Role r = iter.next();
			if (r.getRoleType().name().equals(RoleType.SECURITY_ANALYST.name())) {
				fail("Role " + RoleType.SECURITY_ANALYST.name()
						+ " was not removed from " + user.getUserName());
			}
		}
	}

	/**
	 * 
	 */
	@Test
	public void test_Deleting_User_deletes_UserRoles() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		String firstName = "George";
		String lastName = "Patton";
		String userName = "gpatton@governmentcio.com";
		String password = "bloodandguts";

		String parameters = "?firstName=" + firstName + "&lastName=" + lastName;
		parameters = parameters + "&userName=" + userName + "&password=" + password;

		ResponseEntity<User> userResponse = restTemplate.exchange(
				createURLWithPort("/addUser" + parameters), HttpMethod.POST, entity,
				new ParameterizedTypeReference<User>() {
				});

		assertNotNull(userResponse);

		assertTrue(userResponse.getStatusCode() == HttpStatus.OK);

		User user = userResponse.getBody();

		assertNotNull(user);

		// Add User to Role

		parameters = "?userName=" + user.getUserName() + "&roleType="
				+ RoleType.SECURITY_ANALYST.name() + "&projectId=1000";

		ResponseEntity<Void> responseVoid = restTemplate.exchange(
				createURLWithPort("/addUserToRole" + parameters), HttpMethod.POST,
				entity, new ParameterizedTypeReference<Void>() {
				});

		assertNotNull(responseVoid);

		assertTrue(responseVoid.getStatusCode() == HttpStatus.OK);

		// get User and check for Roles

		userResponse = restTemplate.exchange(
				createURLWithPort("/getUser/" + user.getUserName()), HttpMethod.GET,
				entity, new ParameterizedTypeReference<User>() {
				});

		assertNotNull(userResponse);

		assertTrue(userResponse.getStatusCode() == HttpStatus.OK);

		User userWithRoles = userResponse.getBody();

		assertNotNull(userWithRoles);

		Set<Role> roles = userWithRoles.getRoles();

		Iterator<Role> iter = roles.iterator();

		while (iter.hasNext()) {
			Role r = iter.next();
			assertTrue(
					r.getRoleType().name().equals(RoleType.SECURITY_ANALYST.name()));
		}

		// Remove the User

		responseVoid = restTemplate.exchange(
				createURLWithPort("/removeUser/" + userWithRoles.getUserName()),
				HttpMethod.DELETE, entity, new ParameterizedTypeReference<Void>() {
				});

		assertNotNull(responseVoid);

		assertTrue(responseVoid.getStatusCode() == HttpStatus.OK);

	}

	/**
	 * Returns a valid URL for local host, available port and user supplied
	 * mapping.
	 * 
	 * @param string Mapping to controller function.
	 * @return Valid URL for local host and port.
	 */
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + BASE_URL + uri;
	}

}
