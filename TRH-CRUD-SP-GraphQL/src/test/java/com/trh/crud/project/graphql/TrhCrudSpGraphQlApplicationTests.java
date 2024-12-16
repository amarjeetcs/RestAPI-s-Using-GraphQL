package com.trh.crud.project.graphql;

import com.trh.crud.project.graphql.controller.UserController;
import com.trh.crud.project.graphql.model.User;
import com.trh.crud.project.graphql.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllUsers() {
		User user1 = new User();
		user1.setUserName("John Doe");

		User user2 = new User();
		user2.setUserName("Jane Smith");

		when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

		List<User> users = userController.getAllUsers();

		assertEquals(2, users.size());
		assertEquals("John Doe", users.get(0).getUserName());
		assertEquals("Jane Smith", users.get(1).getUserName());
		verify(userService, times(1)).getAllUsers();
	}

	@Test
	void testGetUserById_UserFound() {
		User user = new User();
		user.setUserName("John Doe");

		when(userService.getUserById(1)).thenReturn(user);

		User foundUser = userController.getUserById(1);

		assertNotNull(foundUser);
		assertEquals("John Doe", foundUser.getUserName());
		verify(userService, times(1)).getUserById(1);
	}

	@Test
	void testGetUserById_UserNotFound() {
		when(userService.getUserById(999)).thenReturn(null);

		User foundUser = userController.getUserById(999);

		assertNull(foundUser);
		verify(userService, times(1)).getUserById(999);
	}

	@Test
	void testCreateUser() {
		User newUser = new User();
		newUser.setUserName("Jane Doe");

		when(userService.createUser(any(User.class))).thenReturn(newUser);

		User createdUser = userController.createUser("Jane Doe", "jane.doe@example.com", "1234567890", "password");

		assertNotNull(createdUser);
		assertEquals("Jane Doe", createdUser.getUserName());
		verify(userService, times(1)).createUser(any(User.class));
	}

//	@Test
//	void testUpdateUser_UserExists() {
//		User existingUser = new User();
//		existingUser.setUserId(1);
//
//		when(userService.updateUser(anyInt(), any(User.class))).thenReturn(existingUser);
//
//		User updatedUser = userController.updateUser(1, "Updated Name", "updated@example.com", "0987654321",
//				"newpassword");
//
//		assertNotNull(updatedUser);
//		assertEquals("Updated Name", updatedUser.getUserName());
//		verify(userService, times(1)).updateUser(eq(1), any(User.class));
//	}

	@Test
	void testDeleteUser_UserExists() {
		when(userService.deleteUser(1)).thenReturn(true);

		boolean isDeleted = userController.deleteUser(1);

		assertTrue(isDeleted);
		verify(userService, times(1)).deleteUser(1);
	}

	@Test
	void testDeleteUser_UserDoesNotExist() {
		when(userService.deleteUser(999)).thenReturn(false);

		boolean isDeleted = userController.deleteUser(999);

		assertFalse(isDeleted);
		verify(userService, times(1)).deleteUser(999);
	}
}