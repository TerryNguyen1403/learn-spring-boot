package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.controller.UserController;
import com.example.demo.dto.UserCreateDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	@Test
	void createUser_whenValid_returnUser() throws Exception {
		// Arrange
		User created = new User("Giang", "giang@gmail.com", "123456Abc!@#");
		when(userService.createUser(any(UserCreateDTO.class))).thenReturn(created);

		// Act + Assert
		String validJson = """
				{
				    "name": "Giang",
				    "email": "giang@gmail.com",
				    "password": "123456Abc!@#"
				}
				""";

		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/users").content(validJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
}
