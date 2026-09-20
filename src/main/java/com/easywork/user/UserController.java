package com.easywork.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

    @PostMapping
	public UserResponse createUser(@RequestBody User user) {
    	
    	
		User savedUser = userService.createUser(user);
		
		return new UserResponse(
				savedUser.getId(),
				savedUser.getUsername(),
				savedUser.getRole());
				
	}

}
