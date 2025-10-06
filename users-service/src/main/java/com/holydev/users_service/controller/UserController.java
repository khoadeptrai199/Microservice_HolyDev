package com.holydev.users_service.controller;

import com.holydev.users_service.Dto.UserDto;
import com.holydev.users_service.feign.dto.OrderDto;
import com.holydev.users_service.feign.repository.OrderFeign;
import com.holydev.users_service.model.User;
import com.holydev.users_service.repository.UserRepository;
import com.holydev.users_service.response.UserResponse;
import com.holydev.users_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderFeign orderFeign;

    @PostMapping
    @CacheEvict(value = "allUsers", allEntries = true)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    @GetMapping("/user/{userId}")
    public UserResponse getUsersByUserId(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<OrderDto> orders = orderFeign.getOrdersByUserId(userId);
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), orders);
    }
}
